package ru.serjik.wallpaper;

import android.view.MotionEvent;

public class OffsetSimulator
{
	private float start, offset;

	public void onTouchEvent(MotionEvent event, WallpaperOffsetsListener wallpaperOffsetsListener, float width)
	{
		if (width < 1.0f)
		{
			return;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			start = event.getX();
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP)
		{
			float delta = (event.getX() - start);
			start = event.getX();

			offset -= delta / width;

			if (offset < -0.5f)
			{
				offset = -0.5f;
			}

			if (offset > 0.5f)
			{
				offset = 0.5f;
			}

			wallpaperOffsetsListener.onOffsetChanged(offset);
		}
	}
}
