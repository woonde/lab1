package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(Color.parseColor("#1A1A2E"));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(0, 0, 0, 40);
        scrollView.addView(layout);

        // 1 задание
        layout.addView(makeLabel(this, "Задание 1 — Серый прямоугольник"));

        RectView rectView = new RectView(this);
        rectView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 720));
        layout.addView(rectView);

        // Задание 2
        layout.addView(makeLabel(this, "Задание 2 — Куб с разноцветными гранями"));

        CubeView cubeView = new CubeView(this);
        cubeView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 960));
        layout.addView(cubeView);

        setContentView(scrollView);

        ViewCompat.setOnApplyWindowInsetsListener(scrollView, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private TextView makeLabel(Context ctx, String text) {
        TextView tv = new TextView(ctx);
        tv.setText(text);
        tv.setTextColor(Color.parseColor("#E0E0E0"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 32, 0, 8);
        tv.setLayoutParams(lp);
        return tv;
    }

    class RectView extends View {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public RectView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(255, 26, 26, 46);

            int margin = 120;
            paint.setColor(Color.GRAY);
            canvas.drawRect(margin, margin,
                    getWidth() - margin, getHeight() - margin, paint);
        }
    }

    class CubeView extends View {
        private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Paint edgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public CubeView(Context context) {
            super(context);
            edgePaint.setStyle(Paint.Style.STROKE);
            edgePaint.setColor(Color.WHITE);
            edgePaint.setStrokeWidth(3f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(255, 26, 26, 46);

            int w = getWidth();
            int h = getHeight();

            float s  = Math.min(w, h) * 0.35f;  // длина ребра
            float ox = s * 0.5f;                  // смещение X
            float oy = s * 0.3f;                  // смещение Y

            // левый-нижний угол
            float cx = w / 2f - ox / 2f;
            float cy = h / 2f + oy / 2f;

            float[] p = {
                    cx,          cy,          // p0
                    cx + s,      cy,          // p1
                    cx + s,      cy + s,      // p2
                    cx,          cy + s,      // p3
                    cx + ox,     cy - oy,     // p4
                    cx + s + ox, cy - oy,     // p5
                    cx + s + ox, cy + s - oy, // p6
                    cx + ox,     cy + s - oy  // p7
            };

            fillPaint.setStyle(Paint.Style.FILL);

            // передняя-синяя грань
            fillPaint.setColor(Color.parseColor("#1565C0"));
            canvas.drawPath(makeFace(p, 0, 1, 2, 3), fillPaint);
            canvas.drawPath(makeFace(p, 0, 1, 2, 3), edgePaint);

            // верхняя-зелёная грань
            fillPaint.setColor(Color.parseColor("#2E7D32"));
            canvas.drawPath(makeFace(p, 0, 1, 5, 4), fillPaint);
            canvas.drawPath(makeFace(p, 0, 1, 5, 4), edgePaint);

            // правая боковая-красная
            fillPaint.setColor(Color.parseColor("#C62828"));
            canvas.drawPath(makeFace(p, 1, 5, 6, 2), fillPaint);
            canvas.drawPath(makeFace(p, 1, 5, 6, 2), edgePaint);
        }
        private Path makeFace(float[] p, int i, int j, int k, int l) {
            Path path = new Path();
            path.moveTo(p[i * 2],     p[i * 2 + 1]);
            path.lineTo(p[j * 2],     p[j * 2 + 1]);
            path.lineTo(p[k * 2],     p[k * 2 + 1]);
            path.lineTo(p[l * 2],     p[l * 2 + 1]);
            path.close();
            return path;
        }
    }
}