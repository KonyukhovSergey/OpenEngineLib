package ru.serjik.wallpaper;

import android.content.Context;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View.OnTouchListener;

import ru.serjik.engine.EngineView;

public abstract class GLWallpaperService extends WallpaperService
{
	@Override
	public Engine onCreateEngine()
	{
		return new WallpaperEngine();
	}

	public class WallpaperEngine extends WallpaperService.Engine
	{
		private WallpaperSurfaceView view = null;

		private WallpaperOffsetsListener wallpaperOffsetsListener;
		private OnTouchListener onTouchListener;

		private OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

		private OffsetWorkingDetector offsetWorkingDetector = new OffsetWorkingDetector();
		private OffsetSimulator offsetSimulator = new OffsetSimulator();

		private class WallpaperSurfaceView extends EngineView
		{
			WallpaperSurfaceView(Context context)
			{
				super(context);
			}

			@Override
			public SurfaceHolder getHolder()
			{
				return getSurfaceHolder();
			}

			public void onDestroy()
			{
				super.onDetachedFromWindow();
			}
		}

		@Override
		public void onVisibilityChanged(boolean visible)
		{
			if (view != null)
			{
				if (visible)
				{
					offsetWorkingDetector.init();
					view.onResume();
				}
				else
				{
					view.onPause();
				}
			}
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder)
		{
			super.onCreate(surfaceHolder);

			view = new WallpaperSurfaceView(getContext());

			view.setEGLContextClientVersion(2);

			view.setRenderer(getRenderer(this, view));
			view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}

		private void unregisterSharedPreferencesListener()
		{
			if (onSharedPreferenceChangeListener != null)
			{
				PreferenceManager.getDefaultSharedPreferences(GLWallpaperService.this).unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
				onSharedPreferenceChangeListener = null;
			}
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder)
		{
			unregisterSharedPreferencesListener();
			view.surfaceDestroyed(holder);
		}

		@Override
		public void onDestroy()
		{
			super.onDestroy();
			if (view != null)
			{
				view.onDestroy();
			}
		}

		@Override
		public void onTouchEvent(MotionEvent event)
		{
			if (view != null)
			{
				if (onTouchListener != null)
				{
					onTouchListener.onTouch(view, event);
				}
				else
				{
					if (wallpaperOffsetsListener != null && offsetWorkingDetector.isOnOffsetsChangedWorking() == false)
					{
						offsetSimulator.onTouchEvent(event, wallpaperOffsetsListener, view.getWidth());
					}
				}
			}
			super.onTouchEvent(event);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset)
		{
			if (wallpaperOffsetsListener != null)
			{
				offsetWorkingDetector.onOffsetsChanged(xOffset, wallpaperOffsetsListener);
			}
		}

		public void setWallpaperOffsetsListener(WallpaperOffsetsListener wallpaperOffsetsListener)
		{
			this.wallpaperOffsetsListener = wallpaperOffsetsListener;
			this.onTouchListener = null;
		}

		public void setOnTouchListener(OnTouchListener onTouchListener)
		{
			this.onTouchListener = onTouchListener;
			this.wallpaperOffsetsListener = null;
		}

		public void setOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener)
		{
			unregisterSharedPreferencesListener();
			this.onSharedPreferenceChangeListener = onSharedPreferenceChangeListener;
			PreferenceManager.getDefaultSharedPreferences(GLWallpaperService.this).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
		}
	}

	public Context getContext()
	{
		return getBaseContext();
	}

	public abstract Renderer getRenderer(WallpaperEngine engine, EngineView view);
}
