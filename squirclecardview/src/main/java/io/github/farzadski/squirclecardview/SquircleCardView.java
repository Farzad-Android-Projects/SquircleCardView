package io.github.farzadski.squirclecardview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class SquircleCardView extends FrameLayout {

  private static final float DEFAULT_CORNER_RADIUS_DP = 24f;
  private static final float DEFAULT_CONTROL_FACTOR = 0.90f;
  private final Paint backgroundPaint;
  private final Path squirclePath;
  private float topLeftRadius;
  private float topRightRadius;
  private float bottomRightRadius;
  private float bottomLeftRadius;
  private float controlFactor;
  private int cardBackgroundColor;

  public SquircleCardView(@NonNull Context context) {
    this(context, null);
  }

  public SquircleCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SquircleCardView(
      @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    squirclePath = new Path();
    initialize(attrs);
  }

  private void initialize(@Nullable AttributeSet attrs) {
    float defaultRadius = dpToPx(DEFAULT_CORNER_RADIUS_DP);
    topLeftRadius = defaultRadius;
    topRightRadius = defaultRadius;
    bottomRightRadius = defaultRadius;
    bottomLeftRadius = defaultRadius;
    controlFactor = DEFAULT_CONTROL_FACTOR;
    cardBackgroundColor = Color.WHITE;

    if (attrs != null) {
      try (TypedArray array =
          getContext().obtainStyledAttributes(attrs, R.styleable.SquircleCardView)) {

        topLeftRadius =
            array.getDimension(R.styleable.SquircleCardView_squircleTopLeftRadius, defaultRadius);

        topRightRadius =
            array.getDimension(R.styleable.SquircleCardView_squircleTopRightRadius, defaultRadius);

        bottomRightRadius =
            array.getDimension(
                R.styleable.SquircleCardView_squircleBottomRightRadius, defaultRadius);

        bottomLeftRadius =
            array.getDimension(
                R.styleable.SquircleCardView_squircleBottomLeftRadius, defaultRadius);

        controlFactor =
            array.getFloat(
                R.styleable.SquircleCardView_squircleControlFactor, DEFAULT_CONTROL_FACTOR);

        cardBackgroundColor =
            array.getColor(R.styleable.SquircleCardView_squircleBackgroundColor, Color.WHITE);
      }
    }

    controlFactor = clamp(controlFactor);
    backgroundPaint.setStyle(Paint.Style.FILL);
    backgroundPaint.setColor(cardBackgroundColor);
    setWillNotDraw(false);
    setClipChildren(false);
    setClipToPadding(false);
    setBackground(null);
  }

  @Override
  protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    updatePath(width, height);
  }

  private void updatePath(int width, int height) {
    if (width <= 0 || height <= 0) {
      return;
    }

    float tl = clampRadius(topLeftRadius, width, height);
    float tr = clampRadius(topRightRadius, width, height);
    float br = clampRadius(bottomRightRadius, width, height);
    float bl = clampRadius(bottomLeftRadius, width, height);
    squirclePath.reset();

    /*
     * TOP LEFT
     */
    squirclePath.moveTo(0, tl);
    addTopLeftCorner(tl);

    /*
     * TOP EDGE
     */
    squirclePath.lineTo(width - tr, 0);

    /*
     * TOP RIGHT
     */
    addTopRightCorner(width, tr);

    /*
     * RIGHT EDGE
     */
    squirclePath.lineTo(width, height - br);

    /*
     * BOTTOM RIGHT
     */
    addBottomRightCorner(width, height, br);

    /*
     * BOTTOM EDGE
     */
    squirclePath.lineTo(bl, height);

    /*
     * BOTTOM LEFT
     */
    addBottomLeftCorner(height, bl);

    /*
     * LEFT EDGE
     */
    squirclePath.lineTo(0, tl);
    squirclePath.close();
    invalidate();
  }

  private void addTopLeftCorner(float radius) {
    float control = radius * controlFactor;
    squirclePath.cubicTo(
        (float) 0,
        (float) 0 + radius - control,
        (float) 0 + radius - control,
        (float) 0,
        (float) 0 + radius,
        (float) 0);
  }

  private void addTopRightCorner(float x, float radius) {
    float control = radius * controlFactor;
    squirclePath.cubicTo(
        x - radius + control, (float) 0, x, (float) 0 + radius - control, x, (float) 0 + radius);
  }

  private void addBottomRightCorner(float x, float y, float radius) {
    float control = radius * controlFactor;
    squirclePath.cubicTo(x, y - radius + control, x - radius + control, y, x - radius, y);
  }

  private void addBottomLeftCorner(float y, float radius) {
    float control = radius * controlFactor;
    squirclePath.cubicTo(
        (float) 0 + radius - control, y, (float) 0, y - radius + control, (float) 0, y - radius);
  }

  @Override
  protected void onDraw(@NonNull Canvas canvas) {
    canvas.drawPath(squirclePath, backgroundPaint);
  }

  @Override
  protected void dispatchDraw(@NonNull Canvas canvas) {
    int saveCount = canvas.save();
    canvas.clipPath(squirclePath);
    super.dispatchDraw(canvas);
    canvas.restoreToCount(saveCount);
  }

  // ---------------------------------------------------------
  // Top Left
  // ---------------------------------------------------------

  public float getTopLeftRadius() {
    return pxToDp(topLeftRadius);
  }

  public void setTopLeftRadius(float radiusDp) {
    topLeftRadius = dpToPx(radiusDp);
    updatePath(getWidth(), getHeight());
  }

  // ---------------------------------------------------------
  // Top Right
  // ---------------------------------------------------------

  public float getTopRightRadius() {
    return pxToDp(topRightRadius);
  }

  public void setTopRightRadius(float radiusDp) {
    topRightRadius = dpToPx(radiusDp);
    updatePath(getWidth(), getHeight());
  }

  // ---------------------------------------------------------
  // Bottom Right
  // ---------------------------------------------------------

  public float getBottomRightRadius() {
    return pxToDp(bottomRightRadius);
  }

  public void setBottomRightRadius(float radiusDp) {
    bottomRightRadius = dpToPx(radiusDp);
    updatePath(getWidth(), getHeight());
  }

  // ---------------------------------------------------------
  // Bottom Left
  // ---------------------------------------------------------

  public float getBottomLeftRadius() {
    return pxToDp(bottomLeftRadius);
  }

  public void setBottomLeftRadius(float radiusDp) {
    bottomLeftRadius = dpToPx(radiusDp);
    updatePath(getWidth(), getHeight());
  }

  // ---------------------------------------------------------
  // Control Factor
  // ---------------------------------------------------------

  public float getControlFactor() {
    return controlFactor;
  }

  public void setControlFactor(float controlFactor) {
    this.controlFactor = clamp(controlFactor);
    updatePath(getWidth(), getHeight());
  }

  // ---------------------------------------------------------
  // Background
  // ---------------------------------------------------------

  public int getCardBackgroundColor() {
    return cardBackgroundColor;
  }

  public void setCardBackgroundColor(int color) {
    cardBackgroundColor = color;
    backgroundPaint.setColor(color);
    invalidate();
  }

  // ---------------------------------------------------------
  // Helpers
  // ---------------------------------------------------------

  private float clampRadius(float radius, int width, int height) {
    return Math.min(Math.max(radius, 0f), Math.min(width, height) / 2f);
  }

  private float clamp(float value) {
    return Math.max((float) 0.0, Math.min(value, (float) 1.0));
  }

  private float dpToPx(float dp) {
    return dp * getResources().getDisplayMetrics().density;
  }

  private float pxToDp(float px) {
    return px / getResources().getDisplayMetrics().density;
  }
}
