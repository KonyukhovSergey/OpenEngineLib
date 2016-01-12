package ru.serjik.wallpaper;

public class OffsetWorkingDetector
{
	private static final int MINIMUM_OFFSET_CHANGE_COUNT = 3;
	private boolean isOnOffsetsChangedWorking = false;
	private float lastOffsetValue = 0;
	private int offsetChangesCount = 0;

	public void init()
	{
		isOnOffsetsChangedWorking = false;
		lastOffsetValue = 0;
		offsetChangesCount = 0;
	}
	
	public void onOffsetsChanged(float xOffset, float yOffset, WallpaperOffsetsListener wallpaperOffsetsListener)
	{
		if (isOnOffsetsChangedWorking)
		{
			if (xOffset < 0.0f || xOffset > 1.0f)
			{
				wallpaperOffsetsListener.onOffsetChanged(0, 0);
			}
			else
			{
				wallpaperOffsetsListener.onOffsetChanged(xOffset - 0.5f, 0);
			}
		}
		else
		{
			if (offsetChangesCount > MINIMUM_OFFSET_CHANGE_COUNT)
			{
				isOnOffsetsChangedWorking = true;
				wallpaperOffsetsListener.onOffsetChanged(xOffset - 0.5f, 0);
			}
			else
			{
				if (Math.abs(xOffset - lastOffsetValue) > 0.001f)
				{
					offsetChangesCount++;
					lastOffsetValue = xOffset;
				}
				else
				{
					offsetChangesCount = 0;
				}
			}
		}
	}

	public boolean isOnOffsetsChangedWorking()
	{
		return isOnOffsetsChangedWorking;
	}
}
