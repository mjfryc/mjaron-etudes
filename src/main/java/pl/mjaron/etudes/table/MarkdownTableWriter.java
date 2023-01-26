/*
 * Copyright  2021  Michał Jaroń <m.jaron@protonmail.com>
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

import pl.mjaron.etudes.Str;

/**
 * Writes table in Markdown style.
 */
public class MarkdownTableWriter implements ITableWriter {

    private final StringBuilder out = new StringBuilder();
    private int[] widths = null;

    private int columnIdx = 0;

    public MarkdownTableWriter() {
    }

    public MarkdownTableWriter(final int[] widths) {
        this.widths = widths;
    }

    /**
     * Set column widths.
     *
     * @param widths Array with column widths.
     * @return This reference.
     */
    public MarkdownTableWriter setWidths(int[] widths) {
        this.widths = widths;
        return this;
    }

    public int[] getWidths() {
        return widths;
    }

    @Override
    public String getTable() {
        return out.toString();
    }

    @Override
    public void beginTable(ITableSource source, int[] widths) {
        if (widths != null) {
            this.widths = widths;
        }
    }

    @Override
    public void endTable() {
    }

    @Override
    public void beginHeader() {
    }

    @Override
    public void endHeader() {
        out.append("|\n");
        for (Integer w : widths) {
            out.append("| ");
            Str.pad(out, w, '-');
            out.append(' ');
        }
        out.append("|\n");
    }

    @Override
    public void beginRow() {
        columnIdx = 0;
    }

    @Override
    public void endRow() {
        out.append("|\n");
    }

    @Override
    public void writeCell(final String what) {
        out.append("| ");
        Str.padLeft(what, widths[columnIdx], ' ', out);
        out.append(' ');
        ++columnIdx;
    }
}
