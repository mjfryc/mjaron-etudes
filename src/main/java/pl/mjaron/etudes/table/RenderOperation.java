/*
 * Copyright  2023  Michał Jaroń <m.jaron@protonmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package pl.mjaron.etudes.table;

public abstract class RenderOperation {

    public static void execute(final RenderContext context) {
        try (RenderRuntime runtime = new RenderRuntime(context)) {
            context.getColumnWidthResolver().resolve(runtime);
            final ITableSource source = runtime.getSource();
            final ITableWriter writer = runtime.getWriter();

            runtime.getEscaper().beginTable(runtime);
            writer.beginTable(runtime);

            if (runtime.getSource().hasHeaders()) {
                runtime.setHeaderState(true);
                writer.beginHeader();
                runtime.resetColumn();
                for (final String header : source.getHeaders()) {
                    writer.writeCell(runtime.getEscaper().escape(header));
                    runtime.nextColumn();
                }
                writer.endHeader();
                runtime.setHeaderState(false);
            }

            for (final Iterable<Object> row : source) {
                writer.beginRow();
                runtime.resetColumn();
                for (final Object cell : row) {
                    writer.writeCell(runtime.getEscaper().escape(cell.toString())); // @todo Use value converter instead of direct toString().
                    runtime.nextColumn();
                }
                writer.endRow();
            }
            writer.endTable();
        } catch (final Exception e) {
            throw new RuntimeException("Render operation failed.", e);
        }
    }
}
