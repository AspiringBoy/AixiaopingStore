package com.axp.axpseller.views.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ArrowKeyMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.TextView;

import com.axp.axpseller.R;
import com.axp.axpseller.models.Spec;
import com.axp.axpseller.models.SpecItem;
import com.axp.axpseller.models.SpecNote;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by xu on 2016/6/16.
 */
public class SpecTagGroup extends ViewGroup {


    public static final int STATUS_SELECT = 1;
    public static final int STATUS_UN_SELECT = 2;
    public static final int STATUS_CANNOT_SELECT = 3;

    private final int default_border_color = Color.rgb(0x49, 0xC1, 0x20);
    private final int default_text_color = Color.rgb(0x49, 0xC1, 0x20);
    private final int default_background_color = Color.WHITE;
    private final int default_dash_border_color = Color.rgb(0xAA, 0xAA, 0xAA);
    private final int default_input_hint_color = Color.argb(0x80, 0x00, 0x00, 0x00);
    private final int default_input_text_color = Color.argb(0xDE, 0x00, 0x00, 0x00);
    private final int default_checked_border_color = Color.rgb(0x49, 0xC1, 0x20);
    private final int default_checked_text_color = Color.WHITE;
    private final int default_checked_marker_color = Color.WHITE;
    private final int default_checked_background_color = Color.rgb(0x49, 0xC1, 0x20);
    private final int default_pressed_background_color = Color.rgb(0xED, 0xED, 0xED);
    private final float default_border_stroke_width;
    private final float default_text_size;
    private final float default_horizontal_spacing;
    private final float default_vertical_spacing;
    private final float default_horizontal_padding;
    private final float default_vertical_padding;

    /**
     * Indicates whether this TagGroup is set up to APPEND mode or DISPLAY mode. Default is false.
     */
    private boolean isAppendMode;

    /**
     * The text to be displayed when the text of the INPUT tag is empty.
     */
    private CharSequence inputHint;

    /**
     * The tag outline border color.
     */
    private int borderColor;

    /**
     * The tag text color.
     */
    private int textColor;

    /**
     * The tag background color.
     */
    private int backgroundColor;

    /**
     * The dash outline border color.
     */
    private int dashBorderColor;

    /**
     * The  input tag hint text color.
     */
    private int inputHintColor;

    /**
     * The input tag type text color.
     */
    private int inputTextColor;

    /**
     * The checked tag outline border color.
     */
    private int checkedBorderColor;

    /**
     * The check text color
     */
    private int checkedTextColor;

    /**
     * The checked marker color.
     */
    private int checkedMarkerColor;

    /**
     * The checked tag background color.
     */
    private int checkedBackgroundColor;

    /**
     * The tag background color, when the tag is being pressed.
     */
    private int pressedBackgroundColor;

    /**
     * The tag outline border stroke width, default is 0.5dp.
     */
    private float borderStrokeWidth;

    /**
     * The tag text size, default is 13sp.
     */
    private float textSize;

    /**
     * The horizontal tag spacing, default is 8.0dp.
     */
    private int horizontalSpacing;

    /**
     * The vertical tag spacing, default is 4.0dp.
     */
    private int verticalSpacing;

    /**
     * The horizontal tag padding, default is 12.0dp.
     */
    private int horizontalPadding;

    /**
     * The vertical tag padding, default is 3.0dp.
     */
    private int verticalPadding;

    /**
     * Listener used to dispatch tag change event.
     */
    private OnTagChangeListener mOnTagChangeListener;

    /**
     * Listener used to dispatch tag click event.
     */
    private OnTagClickListener mOnTagClickListener;

    /**
     * Listener used to handle tag click event.
     */
    private InternalTagClickListener mInternalTagClickListener = new InternalTagClickListener();

    /**
     * 选中的颜色
     */
    private int selectColor = android.R.color.holo_red_dark;
    /**
     * 未选中的颜色
     */
    private int unSelectColor = R.color.font_color;
    /**
     * 禁用选中的颜色
     */
    private int cannotSelectColor = android.R.color.darker_gray;

    public TagView selectTagView;
    public List<Tag> tags = new ArrayList<>();

    public SpecTagGroup(Context context) {
        this(context, null);
    }

    public SpecTagGroup(Context context, AttributeSet attrs) {
        this(context, attrs, me.gujun.android.taggroup.R.attr.tagGroupStyle);
    }

    public SpecTagGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_border_stroke_width = dp2px(0.5f);
        default_text_size = sp2px(13.0f);
        default_horizontal_spacing = dp2px(8.0f);
        default_vertical_spacing = dp2px(4.0f);
        default_horizontal_padding = dp2px(12.0f);
        default_vertical_padding = dp2px(3.0f);

        // Load styled attributes.
        final TypedArray a = context.obtainStyledAttributes(attrs, me.gujun.android.taggroup.R.styleable.TagGroup, defStyleAttr, me.gujun.android.taggroup.R.style.TagGroup);
        try {
            isAppendMode = a.getBoolean(me.gujun.android.taggroup.R.styleable.TagGroup_atg_isAppendMode, false);
            inputHint = a.getText(me.gujun.android.taggroup.R.styleable.TagGroup_atg_inputHint);
            borderColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_borderColor, default_border_color);
            textColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_textColor, default_text_color);
            backgroundColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_backgroundColor, default_background_color);
            dashBorderColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_dashBorderColor, default_dash_border_color);
            inputHintColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_inputHintColor, default_input_hint_color);
            inputTextColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_inputTextColor, default_input_text_color);
            checkedBorderColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_checkedBorderColor, default_checked_border_color);
            checkedTextColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_checkedTextColor, default_checked_text_color);
            checkedMarkerColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_checkedMarkerColor, default_checked_marker_color);
            checkedBackgroundColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_checkedBackgroundColor, default_checked_background_color);
            pressedBackgroundColor = a.getColor(me.gujun.android.taggroup.R.styleable.TagGroup_atg_pressedBackgroundColor, default_pressed_background_color);
            borderStrokeWidth = a.getDimension(me.gujun.android.taggroup.R.styleable.TagGroup_atg_borderStrokeWidth, default_border_stroke_width);
            textSize = a.getDimension(me.gujun.android.taggroup.R.styleable.TagGroup_atg_textSize, default_text_size);
            horizontalSpacing = (int) a.getDimension(me.gujun.android.taggroup.R.styleable.TagGroup_atg_horizontalSpacing, default_horizontal_spacing);
            verticalSpacing = (int) a.getDimension(me.gujun.android.taggroup.R.styleable.TagGroup_atg_verticalSpacing, default_vertical_spacing);
            horizontalPadding = (int) a.getDimension(me.gujun.android.taggroup.R.styleable.TagGroup_atg_horizontalPadding, default_horizontal_padding);
            verticalPadding = (int) a.getDimension(me.gujun.android.taggroup.R.styleable.TagGroup_atg_verticalPadding, default_vertical_padding);
        } finally {
            a.recycle();
        }

        if (isAppendMode) {
            // Append the initial INPUT tag.
            appendInputTag();

            // Set the click listener to detect the end-input event.
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitTag();
                }
            });
        }
    }

    /**
     * Call this to submit the INPUT tag.
     */
    public void submitTag() {
        final TagView inputTag = getInputTag();
        if (inputTag != null && inputTag.isInputAvailable()) {
            inputTag.endInput();

            if (mOnTagChangeListener != null) {
                mOnTagChangeListener.onAppend(SpecTagGroup.this, inputTag.getText().toString());
            }
            appendInputTag();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int row = 0; // The row counter.
        int rowWidth = 0; // Calc the current row width.
        int rowMaxHeight = 0; // Calc the max tag height, in current row.

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();

            if (child.getVisibility() != GONE) {
                rowWidth += childWidth;
                if (rowWidth > widthSize) { // Next line.
                    rowWidth = childWidth; // The next row width.
                    height += rowMaxHeight + verticalSpacing;
                    rowMaxHeight = childHeight; // The next row max height.
                    row++;
                } else { // This line.
                    rowMaxHeight = Math.max(rowMaxHeight, childHeight);
                }
                rowWidth += horizontalSpacing;
            }
        }
        // Account for the last row height.
        height += rowMaxHeight;

        // Account for the padding too.
        height += getPaddingTop() + getPaddingBottom();

        // If the tags grouped in one row, set the width to wrap the tags.
        if (row == 0) {
            width = rowWidth;
            width += getPaddingLeft() + getPaddingRight();
        } else {// If the tags grouped exceed one line, set the width to match the parent.
            width = widthSize;
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
                heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentLeft = getPaddingLeft();
        final int parentRight = r - l - getPaddingRight();
        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();

        int childLeft = parentLeft;
        int childTop = parentTop;

        int rowMaxHeight = 0;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            if (child.getVisibility() != GONE) {
                if (childLeft + width > parentRight) { // Next line
                    childLeft = parentLeft;
                    childTop += rowMaxHeight + verticalSpacing;
                    rowMaxHeight = height;
                } else {
                    rowMaxHeight = Math.max(rowMaxHeight, height);
                }
                child.layout(childLeft, childTop, childLeft + width, childTop + height);

                childLeft += width + horizontalSpacing;
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.tags = getTags();
        ss.checkedPosition = getCheckedTagIndex();
        if (getInputTag() != null) {
            ss.input = getInputTag().getText().toString();
        }
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        setTags(ss.tags);
        TagView checkedTagView = getTagAt(ss.checkedPosition);
        if (checkedTagView != null) {
            checkedTagView.setChecked(true);
        }
        if (getInputTag() != null) {
            getInputTag().setText(ss.input);
        }
    }

    /**
     * Returns the INPUT tag view in this group.
     *
     * @return the INPUT state tag view or null if not exists
     */
    protected TagView getInputTag() {
        if (isAppendMode) {
            final int inputTagIndex = getChildCount() - 1;
            final TagView inputTag = getTagAt(inputTagIndex);
            if (inputTag != null && inputTag.mState == TagView.STATE_INPUT) {
                return inputTag;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Returns the INPUT state tag in this group.
     *
     * @return the INPUT state tag view or null if not exists
     */
    public String getInputTagText() {
        final TagView inputTagView = getInputTag();
        if (inputTagView != null) {
            return inputTagView.getText().toString();
        }
        return null;
    }

    /**
     * Return the last NORMAL state tag view in this group.
     *
     * @return the last NORMAL state tag view or null if not exists
     */
    protected TagView getLastNormalTagView() {
        final int lastNormalTagIndex = isAppendMode ? getChildCount() - 2 : getChildCount() - 1;
        TagView lastNormalTagView = getTagAt(lastNormalTagIndex);
        return lastNormalTagView;
    }

    /**
     * Returns the tag array in group, except the INPUT tag.
     *
     * @return the tag array.
     */
    public String[] getTags() {
        final int count = getChildCount();
        final List<String> tagList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final TagView tagView = getTagAt(i);
            if (tagView.mState == TagView.STATE_NORMAL) {
                tagList.add(tagView.getText().toString());
            }
        }

        return tagList.toArray(new String[tagList.size()]);
    }

    /**
     * @see #setTags(String...)
     */
    public void setTags(List<String> tagList) {
        setTags(tagList.toArray(new String[tagList.size()]));
    }

    /**
     * Set the tags. It will remove all previous tags first.
     *
     * @param tags the tag list to set.
     */
    public void setTags(String... tags) {
        removeAllViews();
        for (final String tag : tags) {
            appendTag(tag);
        }

        if (isAppendMode) {
            appendInputTag();
        }
    }

    public void setSpecTag(List<Tag> tags) {
        this.tags = tags;
        removeAllViews();
        for (Tag tag : tags) {
            appendSpecTag(tag);
        }
    }

    /**
     * Returns the tag view at the specified position in the group.
     *
     * @param index the position at which to get the tag view from.
     * @return the tag view at the specified position or null if the position
     * does not exists within this group.
     */
    protected TagView getTagAt(int index) {
        return (TagView) getChildAt(index);
    }

    /**
     * Returns the checked tag view in the group.
     *
     * @return the checked tag view or null if not exists.
     */
    protected TagView getCheckedTag() {
        final int checkedTagIndex = getCheckedTagIndex();
        if (checkedTagIndex != -1) {
            return getTagAt(checkedTagIndex);
        }
        return null;
    }

    /**
     * Return the checked tag index.
     *
     * @return the checked tag index, or -1 if not exists.
     */
    protected int getCheckedTagIndex() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final TagView tag = getTagAt(i);
            if (tag.isChecked) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Register a callback to be invoked when this tag group is changed.
     *
     * @param l the callback that will run
     */
    public void setOnTagChangeListener(OnTagChangeListener l) {
        mOnTagChangeListener = l;
    }

    /**
     * @see #appendInputTag(String)
     */
    protected void appendInputTag() {
        appendInputTag(null);
    }

    /**
     * Append a INPUT tag to this group. It will throw an exception if there has a previous INPUT tag.
     *
     * @param tag the tag text.
     */
    protected void appendInputTag(String tag) {
//        final TagView previousInputTag = getInputTag();
//        if (previousInputTag != null) {
//            throw new IllegalStateException("Already has a INPUT tag in group.");
//        }
//
//        final TagView newInputTag = new TagView(getContext(), TagView.STATE_INPUT, tag);
//        newInputTag.setOnImageViewClickListener(mInternalTagClickListener);
//        addView(newInputTag);
    }

    /**
     * Append tag to this group.
     *
     * @param tag the tag to append.
     */
    protected void appendTag(CharSequence tag) {
//        final TagView newTag = new TagView(getContext(), TagView.STATE_NORMAL, tag);
//        newTag.setOnImageViewClickListener(mInternalTagClickListener);
//        addView(newTag);
    }

    protected void appendSpecTag(Tag tag) {
        final TagView newTag = new TagView(getContext(), TagView.STATE_NORMAL, tag);
        newTag.setOnClickListener(mInternalTagClickListener);
        newTag.setTag(tag);
        if (tag.status == STATUS_SELECT) {
            newTag.selection();
        } else if (tag.status == STATUS_CANNOT_SELECT) {
            newTag.cannotSelection();
        } else {
            newTag.unSelection();
        }
        addView(newTag);
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new TagGroup.LayoutParams(getContext(), attrs);
    }

    /**
     * Register a callback to be invoked when a tag is clicked.
     *
     * @param l the callback that will run.
     */
    public void setOnTagClickListener(OnTagClickListener l) {
        mOnTagClickListener = l;
    }

    protected void deleteTag(TagView tagView) {
        removeView(tagView);
        if (mOnTagChangeListener != null) {
            mOnTagChangeListener.onDelete(SpecTagGroup.this, tagView.getText().toString());
        }
    }

    /**
     * Interface definition for a callback to be invoked when a tag group is changed.
     */
    public interface OnTagChangeListener {
        /**
         * Called when a tag has been appended to the group.
         *
         * @param tag the appended tag.
         */
        void onAppend(SpecTagGroup tagGroup, String tag);

        /**
         * Called when a tag has been deleted from the the group.
         *
         * @param tag the deleted tag.
         */
        void onDelete(SpecTagGroup tagGroup, String tag);
    }

    /**
     * Interface definition for a callback to be invoked when a tag is clicked.
     */
    public interface OnTagClickListener {
        /**
         * Called when a tag has been clicked.
         *
         * @param tag The tag text of the tag that was clicked.
         */
        void onTagClick(Tag tag);
    }

    /**
     * Per-child layout information for layouts.
     */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }

    /**
     * For {@link TagGroup} save and restore state.
     */
    static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
        int tagCount;
        String[] tags;
        int checkedPosition;
        String input;

        public SavedState(Parcel source) {
            super(source);
            tagCount = source.readInt();
            tags = new String[tagCount];
            source.readStringArray(tags);
            checkedPosition = source.readInt();
            input = source.readString();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            tagCount = tags.length;
            dest.writeInt(tagCount);
            dest.writeStringArray(tags);
            dest.writeInt(checkedPosition);
            dest.writeString(input);
        }
    }

    /**
     * The tag view click listener for internal use.
     */
    class InternalTagClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            final TagView tagView = (TagView) v;
//            if (isAppendMode) {
//                if (tag.mState == TagView.STATE_INPUT) {
//                    // If the clicked tag is in INPUT state, uncheck the previous checked tag if exists.
//                    final TagView checkedTag = getCheckedTag();
//                    if (checkedTag != null) {
//                        checkedTag.setChecked(false);
//                    }
//                } else {
//                    // If the clicked tag is currently checked, delete the tag.
//                    if (tag.isChecked) {
//                        deleteTag(tag);
//                    } else {
//                        // If the clicked tag is unchecked, uncheck the previous checked tag if exists,
//                        // then check the clicked tag.
//                        final TagView checkedTag = getCheckedTag();
//                        if (checkedTag != null) {
//                            checkedTag.setChecked(false);
//                        }
//                        tag.setChecked(true);
//                    }
//                }
//            } else {
//
//            }
//            tag.setChecked(true);
            if (mOnTagClickListener != null) {
                Tag tag = (Tag) tagView.getTag();
                if (tag.status == STATUS_SELECT) {
                    Log.e("test", "----" + tag.status);
                    tag.status = STATUS_UN_SELECT;
                    setSpecTag(tags);
                } else if (tag.status == STATUS_CANNOT_SELECT) {
                    Log.e("test", "----" + tag.status);
                    return;
                } else {
                    for (Tag _tag : tags) {
                        if (_tag.status == STATUS_SELECT) {
                            _tag.status = STATUS_UN_SELECT;
                        }
                    }
                    tag.status = STATUS_SELECT;
                    setSpecTag(tags);
                }
                mOnTagClickListener.onTagClick(tag);
            }
        }
    }

    /**
     * The tag view which has two states can be either NORMAL or INPUT.
     */
    class TagView extends TextView {

        private Tag mTag;

        public static final int STATE_NORMAL = 1;
        public static final int STATE_INPUT = 2;

        /**
         * The offset to the text.
         */
        private static final int CHECKED_MARKER_OFFSET = 3;

        /**
         * The stroke width of the checked marker
         */
        private static final int CHECKED_MARKER_STROKE_WIDTH = 4;

        /**
         * The current state.
         */
        private int mState;

        /**
         * Indicates the tag if checked.
         */
        private boolean isChecked = false;

        /**
         * Indicates the tag if pressed.
         */
        private boolean isPressed = false;

        private Paint mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        private Paint mCheckedMarkerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        /**
         * The rect for the tag's left corner drawing.
         */
        private RectF mLeftCornerRectF = new RectF();

        /**
         * The rect for the tag's right corner drawing.
         */
        private RectF mRightCornerRectF = new RectF();

        /**
         * The rect for the tag's horizontal blank fill area.
         */
        private RectF mHorizontalBlankFillRectF = new RectF();

        /**
         * The rect for the tag's vertical blank fill area.
         */
        private RectF mVerticalBlankFillRectF = new RectF();

        /**
         * The rect for the checked mark draw bound.
         */
        private RectF mCheckedMarkerBound = new RectF();

        /**
         * Used to detect the touch event.
         */
        private Rect mOutRect = new Rect();

        /**
         * The path for draw the tag's outline border.
         */
        private Path mBorderPath = new Path();

        /**
         * The path effect provide draw the dash border.
         */
        private PathEffect mPathEffect = new DashPathEffect(new float[]{10, 5}, 0);

        {
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setStrokeWidth(borderStrokeWidth);
            mBackgroundPaint.setStyle(Paint.Style.FILL);
            mCheckedMarkerPaint.setStyle(Paint.Style.FILL);
            mCheckedMarkerPaint.setStrokeWidth(CHECKED_MARKER_STROKE_WIDTH);
            mCheckedMarkerPaint.setColor(checkedMarkerColor);
        }


        public TagView(Context context, final int state, Tag tag) {
            super(context);

            mTag = tag;

            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
            setLayoutParams(new TagGroup.LayoutParams(
                    TagGroup.LayoutParams.WRAP_CONTENT,
                    TagGroup.LayoutParams.WRAP_CONTENT));

            setGravity(Gravity.CENTER);
            setText(tag.tagName);
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

            mState = state;

            setClickable(isAppendMode);
            setFocusable(state == STATE_INPUT);
            setFocusableInTouchMode(state == STATE_INPUT);
            setHint(state == STATE_INPUT ? inputHint : null);
            setMovementMethod(state == STATE_INPUT ? ArrowKeyMovementMethod.getInstance() : null);

            // Interrupted long click event to avoid PAUSE popup.
            setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return state != STATE_INPUT;
                }
            });

            if (state == STATE_INPUT) {
                requestFocus();

                // Handle the ENTER key down.
                setOnEditorActionListener(new OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_NULL
                                && (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                                && event.getAction() == KeyEvent.ACTION_DOWN)) {
                            if (isInputAvailable()) {
                                // If the input content is available, end the input and dispatch
                                // the event, then append a new INPUT state tag.
                                endInput();
                                if (mOnTagChangeListener != null) {
                                    mOnTagChangeListener.onAppend(SpecTagGroup.this, getText().toString());
                                }
                                appendInputTag();
                            }
                            return true;
                        }
                        return false;
                    }
                });

                // Handle the BACKSPACE key down.
                setOnKeyListener(new OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                            // If the input content is empty, check or remove the last NORMAL state tag.
                            if (TextUtils.isEmpty(getText().toString())) {
                                TagView lastNormalTagView = getLastNormalTagView();
                                if (lastNormalTagView != null) {
                                    if (lastNormalTagView.isChecked) {
                                        removeView(lastNormalTagView);
                                        if (mOnTagChangeListener != null) {
                                            mOnTagChangeListener.onDelete(SpecTagGroup.this, lastNormalTagView.getText().toString());
                                        }
                                    } else {
                                        final TagView checkedTagView = getCheckedTag();
                                        if (checkedTagView != null) {
                                            checkedTagView.setChecked(false);
                                        }
                                        lastNormalTagView.setChecked(true);
                                    }
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                });

                // Handle the INPUT tag content changed.
                addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        // When the INPUT state tag changed, uncheck the checked tag if exists.
                        final TagView checkedTagView = getCheckedTag();
                        if (checkedTagView != null) {
                            checkedTagView.setChecked(false);
                        }
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }

//            invalidatePaint();
        }

        /**
         * Set whether this tag view is in the checked state.
         *
         * @param checked true is checked, false otherwise
         */
        public void setChecked(boolean checked) {
            isChecked = checked;
            // Make the checked mark drawing region.
            setPadding(horizontalPadding,
                    verticalPadding,
                    isChecked ? (int) (horizontalPadding + getHeight() / 2.5f + CHECKED_MARKER_OFFSET)
                            : horizontalPadding,
                    verticalPadding);
            invalidatePaint();
        }


        /**
         * Call this method to end this tag's INPUT state.
         */
        public void endInput() {
            // Make the view not focusable.
            setFocusable(false);
            setFocusableInTouchMode(false);
            // Set the hint empty, make the TextView measure correctly.
            setHint(null);
            // Take away the cursor.
            setMovementMethod(null);

            mState = STATE_NORMAL;
            invalidatePaint();
            requestLayout();
        }

        @Override
        protected boolean getDefaultEditable() {
            return true;
        }

        /**
         * Indicates whether the input content is available.
         *
         * @return True if the input content is available, false otherwise.
         */
        public boolean isInputAvailable() {
            return getText() != null && getText().length() > 0;
        }

        private void invalidatePaint() {
            if (isAppendMode) {
                if (mState == STATE_INPUT) {
                    mBorderPaint.setColor(dashBorderColor);
                    mBorderPaint.setPathEffect(mPathEffect);
                    mBackgroundPaint.setColor(backgroundColor);
                    setHintTextColor(inputHintColor);
                    setTextColor(inputTextColor);
                } else {
                    mBorderPaint.setPathEffect(null);
                    if (isChecked) {
                        mBorderPaint.setColor(checkedBorderColor);
                        mBackgroundPaint.setColor(checkedBackgroundColor);
                        setTextColor(checkedTextColor);
                    } else {
                        mBorderPaint.setColor(borderColor);
                        mBackgroundPaint.setColor(backgroundColor);
                        setTextColor(textColor);
                    }
                }
            } else {
                mBorderPaint.setColor(borderColor);
                mBackgroundPaint.setColor(backgroundColor);
                setTextColor(textColor);
            }

            if (isPressed) {
                mBackgroundPaint.setColor(pressedBackgroundColor);
            }
        }

        public void selection() {

            if (selectTagView == null) {
                selectTagView = this;
            } else {
                selectTagView = this;
            }

            setTextColor(getResources().getColor(selectColor));
            mBorderPaint.setColor(getResources().getColor(selectColor));
            mBackgroundPaint.setColor(backgroundColor);
            invalidate();
        }

        public void unSelection() {
            setTextColor(getResources().getColor(unSelectColor));
            mBackgroundPaint.setColor(backgroundColor);
            mBorderPaint.setColor(getResources().getColor(unSelectColor));
            invalidate();
        }

        public void cannotSelection() {
            setTextColor(getResources().getColor(cannotSelectColor));
            mBackgroundPaint.setColor(backgroundColor);
            mBorderPaint.setColor(getResources().getColor(cannotSelectColor));
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawArc(mLeftCornerRectF, -180, 90, true, mBackgroundPaint);
            canvas.drawArc(mLeftCornerRectF, -270, 90, true, mBackgroundPaint);
            canvas.drawArc(mRightCornerRectF, -90, 90, true, mBackgroundPaint);
            canvas.drawArc(mRightCornerRectF, 0, 90, true, mBackgroundPaint);
            canvas.drawRect(mHorizontalBlankFillRectF, mBackgroundPaint);
            canvas.drawRect(mVerticalBlankFillRectF, mBackgroundPaint);

            if (isChecked) {
                canvas.save();
                canvas.rotate(45, mCheckedMarkerBound.centerX(), mCheckedMarkerBound.centerY());
                canvas.drawLine(mCheckedMarkerBound.left, mCheckedMarkerBound.centerY(),
                        mCheckedMarkerBound.right, mCheckedMarkerBound.centerY(), mCheckedMarkerPaint);
                canvas.drawLine(mCheckedMarkerBound.centerX(), mCheckedMarkerBound.top,
                        mCheckedMarkerBound.centerX(), mCheckedMarkerBound.bottom, mCheckedMarkerPaint);
                canvas.restore();
            }
            canvas.drawPath(mBorderPath, mBorderPaint);
            super.onDraw(canvas);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            int left = (int) borderStrokeWidth;
            int top = (int) borderStrokeWidth;
            int right = (int) (left + w - borderStrokeWidth * 2);
            int bottom = (int) (top + h - borderStrokeWidth * 2);

            int d = bottom - top;

            mLeftCornerRectF.set(left, top, left + d, top + d);
            mRightCornerRectF.set(right - d, top, right, top + d);

            mBorderPath.reset();
            mBorderPath.addArc(mLeftCornerRectF, -180, 90);
            mBorderPath.addArc(mLeftCornerRectF, -270, 90);
            mBorderPath.addArc(mRightCornerRectF, -90, 90);
            mBorderPath.addArc(mRightCornerRectF, 0, 90);

            int l = (int) (d / 2.0f);
            mBorderPath.moveTo(left + l, top);
            mBorderPath.lineTo(right - l, top);

            mBorderPath.moveTo(left + l, bottom);
            mBorderPath.lineTo(right - l, bottom);

            mBorderPath.moveTo(left, top + l);
            mBorderPath.lineTo(left, bottom - l);

            mBorderPath.moveTo(right, top + l);
            mBorderPath.lineTo(right, bottom - l);

            mHorizontalBlankFillRectF.set(left, top + l, right, bottom - l);
            mVerticalBlankFillRectF.set(left + l, top, right - l, bottom);

            int m = (int) (h / 2.5f);
            h = bottom - top;
            mCheckedMarkerBound.set(right - m - horizontalPadding + CHECKED_MARKER_OFFSET,
                    top + h / 2 - m / 2,
                    right - horizontalPadding + CHECKED_MARKER_OFFSET,
                    bottom - h / 2 + m / 2);

            // Ensure the checked mark drawing region is correct across screen orientation changes.
            if (isChecked) {
                setPadding(horizontalPadding,
                        verticalPadding,
                        (int) (horizontalPadding + h / 2.5f + CHECKED_MARKER_OFFSET),
                        verticalPadding);
            }
        }

        @Override
        public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
            return new ZanyInputConnection(super.onCreateInputConnection(outAttrs), true);
        }

        /**
         * Solve edit text delete(backspace) key detect, see<a href="http://stackoverflow.com/a/14561345/3790554">
         * Android: Backspace in WebView/BaseInputConnection</a>
         */
        private class ZanyInputConnection extends InputConnectionWrapper {
            public ZanyInputConnection(android.view.inputmethod.InputConnection target, boolean mutable) {
                super(target, mutable);
            }

            @Override
            public boolean deleteSurroundingText(int beforeLength, int afterLength) {
                // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
                if (beforeLength == 1 && afterLength == 0) {
                    // backspace
                    return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                            && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
                }
                return super.deleteSurroundingText(beforeLength, afterLength);
            }
        }
    }

    public static class Tag {

        public List<Spec> specList;
        public List<SpecNote> specNoteList;
        public List<Tag> tags;
        public String tagId;
        public String tagName;
        public int status = SpecTagGroup.STATUS_CANNOT_SELECT;

        /**
         * 获取库存，唯一不同的地方是，是用来判断传进的key是否在所有有库存的规格里有匹配，就算传的key不全，比如有三个规格，只传了一个或两个key
         * @param keys
         * @return
         */
        public boolean getInventory(String[] keys) {
            List<SpecNote> specNotes = specNoteList;
            for (int i = 0; i < keys.length; i++) {
                specNotes = recursion(specNotes, keys[i]);
            }

            if (specNotes.size() == 0) {
                return false;
            }


            return true;
        }

        /**
         * 验证库存， 得出用户可对tag的操作
         * 简而言之，就是让不让用户选
         */
        public int verifyInventory(String[] selectKeys) {

            for (String selectKey : selectKeys) {
                if (selectKey.equals(tagId)) {
                    return SpecTagGroup.STATUS_SELECT;
                }
            }
            int status = SpecTagGroup.STATUS_CANNOT_SELECT;

            //得到和当前选中项相关并且不等于零的的库存
            boolean canSelect = getStockSpecNote(selectKeys);
            if (!canSelect) {
                return SpecTagGroup.STATUS_CANNOT_SELECT;
            } else {
                if(selectKeys == null || selectKeys.length == 0){
                    return SpecTagGroup.STATUS_UN_SELECT;
                }
            }

            X:for (int i = 0; i < selectKeys.length; i++) {
                String selectKey = selectKeys[i];

                //收集排除当前选中项的其他选中项
                List<String> otherSelectKeys = new ArrayList<>();
                for (int j = 0; j < selectKeys.length; j++) {
                    if(!selectKeys[j].equals(selectKeys[i])){
                        otherSelectKeys.add(selectKeys[j]);
                    }
                }

                for (int j = 0; j < this.specNoteList.size(); j++) {
                    SpecNote specNote = this.specNoteList.get(j);
                    for (int i1 = 0; i1 < specNote.getKey().length; i1++) {
                        //判断和当前选中其中一项的组
                        if(specNote.getKey()[i1].equals(selectKey)){
                            //将当前tag加入选中项
                            otherSelectKeys.add(tagId);

                            //判断是否可选
                            if(getInventory(otherSelectKeys.toArray(new String[otherSelectKeys.size()]))){
                                status = SpecTagGroup.STATUS_UN_SELECT;
                                break X;
                            }
                        }
                    }
                }
            }

            //同级别的标签不进行库存判断
//            X:for(Tag tag : tags) {
//                for(String selectKey : selectKeys){
//                    if(selectKey.equals(tag.tagId)){
//                        List<SpecNote> _specNs = getStockSpecNote(new String[]{ tagId});
//                        if(_specNs != null && _specNs.size() > 0){
//                            status = SpecTagGroup.STATUS_UN_SELECT;
//                            break X;
//                        }
//
//                    }
//                }
//            }

            return status;
        }

        /**
         * 得到和选中项和当前项相关的库存
         *
         * @param selectKeys
         * @return 能否选中
         */
        private boolean getStockSpecNote(String[] selectKeys) {

            //要得出相关的库存，就要加上自己
            List<String> _selectKeys = new ArrayList<>();
            X:for (String selectKey : selectKeys) {

                //判断选中key如果适合自己同一个组的则不加入库存查询
                for (int i = 0; i < specList.size(); i++) {
                    Spec spec = specList.get(i);
                    for (int j = 0; j < spec.getSpecItems().size(); j++) {
                        SpecItem item = spec.getSpecItems().get(j);
                        if(item.getSpecItemId().equals(selectKey)){
                            for (int i1 = 0; i1 < spec.getSpecItems().size(); i1++) {
                                //判断选中的key和当前tag是否在同一组
                                String spceItemId = spec.getSpecItems().get(i1).getSpecItemId();
                                if(tagId.equals(spceItemId)){
                                    continue X;
                                }
                            }
                        }
                    }
                }
                _selectKeys.add(selectKey);
            }
            _selectKeys.add(tagId);

            List<SpecNote> specNotes = specNoteList;
            for (String selectKey : _selectKeys) {
                specNotes = recursion(specNotes, selectKey);
            }

//            if (selectKeys.length >= 2 && specNotes.size() == 0) {
//                /**
//                 * 比如规格为两项时，分为如下
//                 * 1 2 3
//                 * 4 5 6
//                 *
//                 * 6对应的 1 2 3都有库存，但是如果只是用以上代码只能实现，选中6 1后，其他的都不能选了
//                 * 用户只能点掉1后再选中其他项
//                 */
//                String[] simpleSelectKeys;
//
//                for (int i = 0; i < selectKeys.length; i++) {
//                    simpleSelectKeys = selectKeys.clone();
//                    simpleSelectKeys[i] = tagId;
//
//                    List<SpecNote> simpleSpecNoteList = specNoteList;
//
//                    for (String simpleSelectKey : simpleSelectKeys) {
//                        simpleSpecNoteList = recursion(simpleSpecNoteList, simpleSelectKey);
//                    }
//
//                    if (simpleSpecNoteList.size() != 0) {
//                        return simpleSpecNoteList != null && simpleSpecNoteList.size() != 0;
//                    }
//
//                }
//            }


            return specNotes != null && specNotes.size() != 0;
        }

        /**
         * 通过选中的规格筛选规格对应的商品信息
         *
         * @param selectKeys
         * @return
         */
        public static SpecNote getSpecNote(List<SpecNote> specNotes, String[] selectKeys) {
            List<SpecNote> _specNotes = specNotes;
            for (String selectKey : selectKeys) {
                _specNotes = recursion(_specNotes, selectKey);
            }

            if (_specNotes.size() == 1) {
                return _specNotes.get(0);
            }

            return null;
        }

        /**
         * 筛选出匹配的库存
         *
         * @param selectKey
         * @return
         */
        private static List<SpecNote> recursion(List<SpecNote> specNoteList, String selectKey) {
            List<SpecNote> specNotes = new ArrayList<>();
            for (SpecNote specNote : specNoteList) {
                for (String key : specNote.getKey()) {
                    if (key.equals(selectKey) && specNote.getStockNumber() > 0) {
                        specNotes.add(specNote);
                    }
                }
            }

            return specNotes;
        }
    }
}