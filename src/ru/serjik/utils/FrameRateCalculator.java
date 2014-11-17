package ru.serjik.utils;

import android.os.SystemClock;

public class FrameRateCalculator
{
	private FrameRateUpdater frameRateUpdater = null;

	private int calcTimePeriod;
	private int framesCount = 0;
	private long lastTime;
	private long frameStartTime;
	private float averageFrameRate = 0;
	private long renderTime = 0;

	private String frameString = "";

	public FrameRateCalculator(FrameRateUpdater frameRateUpdater, int calcTimePeriod)
	{
		this.frameRateUpdater = frameRateUpdater;
		this.calcTimePeriod = calcTimePeriod;
		lastTime = SystemClock.elapsedRealtime();
	}
	
	public FrameRateCalculator(FrameRateUpdater frameRateUpdater)
	{
		this(frameRateUpdater, 3000);
	}
	
	public FrameRateCalculator()
	{
		this(null);
	}

	public void frameBegin()
	{
		frameStartTime = SystemClock.elapsedRealtime();
	}

	public String frameDone()
	{
		framesCount++;

		long currentTime = SystemClock.elapsedRealtime();
		long period = currentTime - lastTime;
		renderTime += currentTime - frameStartTime;

		if (period > calcTimePeriod)
		{
			averageFrameRate = (float) (framesCount * 1000) / (float) period;

			lastTime = currentTime;

			frameString = String.format("%4.1f %d", averageFrameRate, renderTime / framesCount);

			framesCount = 0;
			renderTime = 0;

			if (frameRateUpdater != null)
			{
				frameRateUpdater.onFrameRateUpdate(this);
			}
		}

		return frameString;
	}

	public float frameRate()
	{
		return averageFrameRate;
	}

	public String frameString()
	{
		return frameString;
	}
	
	public void setFameRateUpdater(FrameRateUpdater frameRateUpdater)
	{
		this.frameRateUpdater = frameRateUpdater;
	}
	
	public interface FrameRateUpdater
	{
		void onFrameRateUpdate(FrameRateCalculator frameRateCalculator);
	}
}
