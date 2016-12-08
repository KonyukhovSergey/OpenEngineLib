package ru.serjik.wallpaper;

public class OffsetWorkingDetector
{
	private static final int MINIMUM_OFFSET_CHANGES_COUNT = 3;
	private boolean isOnOffsetsChangedWorking = false;
	private float lastOffsetValue = 0;
	private int offsetChangesCount = 0;

	public void init()
	{
		isOnOffsetsChangedWorking = false;
		lastOffsetValue = 0;
		offsetChangesCount = 0;
	}
	
	public void onOffsetsChanged(float offset, WallpaperOffsetsListener wallpaperOffsetsListener)
	{
		if (isOnOffsetsChangedWorking)
		{
			if (offset >= 0.0f && offset <= 1.0f)
			{
				wallpaperOffsetsListener.onOffsetChanged(offset - 0.5f);
			}
		}
		else
		{
			if (offsetChangesCount > MINIMUM_OFFSET_CHANGES_COUNT)
			{
				isOnOffsetsChangedWorking = true;
			}
			else
			{
				if (Math.abs(offset - lastOffsetValue) > 0.0001f)
				{
					offsetChangesCount++;
					lastOffsetValue = offset;
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
