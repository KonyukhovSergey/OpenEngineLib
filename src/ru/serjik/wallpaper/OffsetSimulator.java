package ru.serjik.wallpaper;

import android.view.MotionEvent;

public class OffsetSimulator
{
	private float startX, offset;

	public void onTouchEvent(MotionEvent event, WallpaperOffsetsListener wallpaperOffsetsListener, float width)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			startX = event.getX();
		}
		
		if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP)
		{
			float dx = (event.getX() - startX);
			startX = event.getX();

			offset -= dx / width;

			if (offset < -0.5f)
			{
				offset = -0.5f;
			}
			if (offset > 0.5f)
			{
				offset = 0.5f;
			}
			wallpaperOffsetsListener.onOffsetChanged(offset, 0);
		}
	}

}
