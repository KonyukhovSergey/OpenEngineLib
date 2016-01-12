package ru.serjik.wallpaper;

import android.content.Context;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View.OnTouchListener;
import ru.serjik.engine.utils.RenderRequester;

public abstract class GLWallpaperService extends WallpaperService
{
	@Override
	public Engine onCreateEngine()
	{
		return new GLWallpaperEngine();
	}

	public class GLWallpaperEngine extends WallpaperService.Engine
	{
		private LiveWallpaperGLSurfaceView view = null;
		private RenderRequester renderRequester = new RenderRequester();
		private WallpaperOffsetsListener wallpaperOffsetsListener;
		private OnTouchListener onTouchListener;
		private OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

		private OffsetWorkingDetector offsetWorkingDetector = new OffsetWorkingDetector();
		private OffsetSimulator offsetSimulator = new OffsetSimulator();

		@Override
		public void onVisibilityChanged(boolean visible)
		{
			if (view != null)
			{
				if (visible)
				{
					offsetWorkingDetector.init();
					view.onResume();
					renderRequester.resume(view);
				}
				else
				{
					renderRequester.pause();
					view.onPause();
				}
			}
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder)
		{
			view = new LiveWallpaperGLSurfaceView(GLWallpaperService.this);

			view.setEGLContextClientVersion(2);
			view.setRenderer(getRenderer(this));
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
			view.onDestroy();
		}

		@Override
		public void onTouchEvent(MotionEvent event)
		{
			if (onTouchListener != null)
			{
				onTouchListener.onTouch(view, event);
			}
			else
			{
				if (offsetWorkingDetector.isOnOffsetsChangedWorking() == false && wallpaperOffsetsListener != null)
				{
					offsetSimulator.onTouchEvent(event, wallpaperOffsetsListener, 720);
				}
			}

			super.onTouchEvent(event);
		}

		public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset)
		{
			if (wallpaperOffsetsListener != null)
			{
				offsetWorkingDetector.onOffsetsChanged(xOffset, yOffset, wallpaperOffsetsListener);
			}
		}

		private class LiveWallpaperGLSurfaceView extends GLSurfaceView
		{
			LiveWallpaperGLSurfaceView(Context context)
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

		public RenderRequester getRenderRequester()
		{
			return renderRequester;
		}

		public void setOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener)
		{
			unregisterSharedPreferencesListener();
			this.onSharedPreferenceChangeListener = onSharedPreferenceChangeListener;
			PreferenceManager.getDefaultSharedPreferences(GLWallpaperService.this).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
		}
	}

	public abstract Renderer getRenderer(GLWallpaperEngine engine);

}
