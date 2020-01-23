package com.purplepath.purplepath.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import android.text.Layout.Alignment;
/**
 * Created by dinesh on 03/10/17.
 */

public class EllipsizedMultilineTextView extends AppCompatTextView {
    public static final int ALL_AVAILABLE = -1;
    private int mMaxLines;

    public EllipsizedMultilineTextView(Context context) {
        super(context);
    }
    public EllipsizedMultilineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public EllipsizedMultilineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private final void init(Context context, AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs,
                new int[] { android.R.attr.maxLines });
        setMaxLines(a.getInt(0, 2));
    }
    @Override
    public void setMaxLines(int maxlines) {
        super.setMaxLines(maxlines);
        mMaxLines = maxlines;
    }
    /**
     * Ellipsize just the last line of text in this view and set the text to the
     * new ellipsized value.
     * @param text Text to set and ellipsize
     * @param avail available width in pixels for the last line
     * @return the {@link CharSequence} that was set on the {@link TextView}
     */
    public CharSequence setText(final CharSequence text, int avail) {
        if (text == null || text.length() == 0) {
            return text;
        }
        setEllipsize(null);
        setText(text);
        if (avail == ALL_AVAILABLE) {
            return text;
        }
        Layout layout = getLayout();
        if (layout == null) {
            final int w = getWidth() - getCompoundPaddingLeft() - getCompoundPaddingRight();
            layout = new StaticLayout(text, 0, text.length(), getPaint(), w, Alignment.ALIGN_NORMAL,
                    1.0f, 0f, false);
        }
        // find the last line of text and chop it according to available space
        final int lastLineStart = layout.getLineStart(mMaxLines - 1);
        final CharSequence remainder = TextUtils.ellipsize(text.subSequence(lastLineStart,
                text.length()), getPaint(), avail, TextUtils.TruncateAt.END);
        // assemble just the text portion, without spans
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text.toString(), 0, lastLineStart);
        if (!TextUtils.isEmpty(remainder)) {
            builder.append(remainder.toString());
        }
        // Now copy the original spans into the assembled string, modified for any ellipsizing.
        //
        // Merely assembling the Spanned pieces together would result in duplicate CharacterStyle
        // spans in the assembled version if a CharacterStyle spanned across the lastLineStart
        // offset.
        if (text instanceof Spanned) {
            final Spanned s = (Spanned) text;
            final Object[] spans = s.getSpans(0, s.length(), Object.class);
            final int destLen = builder.length();
            for (int i = 0; i < spans.length; i++) {
                final Object span = spans[i];
                final int start = s.getSpanStart(span);
                final int end = s.getSpanEnd(span);
                final int flags = s.getSpanFlags(span);
                if (start <= destLen) {
                    builder.setSpan(span, start, Math.min(end, destLen), flags);
                }
            }
        }
        setText(builder);
        return builder;
    }
}