package ru.serjik.wallpaper;

import ru.serjik.engine.utils.RenderRequester;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public abstract class GLWallpaperService extends WallpaperService
{
	@Override
	public Engine onCreateEngine()
	{
		return new GLWallpaperEngine();
	}

	public class GLWallpaperEngine extends WallpaperService.Engine
	{
		private GLSurfaceView view = null;
		private RenderRequester renderRequester = new RenderRequester();

		@Override
		public void onVisibilityChanged(boolean visible)
		{
			if (visible)
			{
				onResume();
				renderRequester.resume(view);
			}
			else
			{
				renderRequester.pause();
				onPause();
			}
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder)
		{
			// Log.v("glws", "onSurfaceCreated");
			view = new GLSurfaceView(GLWallpaperService.this)
			{
				@Override
				public SurfaceHolder getHolder()
				{
					return GLWallpaperEngine.this.getSurfaceHolder();
				}
			};
			onRendererAcquire(view);
			view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder)
		{
			// Log.v("glws", "onSurfaceDestroyed");
			view.surfaceDestroyed(holder);
		}
	}

	public abstract void onRendererAcquire(GLSurfaceView view);

	public void onResume()
	{
		// Log.v("glws", "onResume");
	};

	public void onPause()
	{
		// Log.v("glws", "onPause");
	};
}
