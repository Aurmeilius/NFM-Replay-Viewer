import java.awt.Color;
import java.awt.Graphics2D;

class Slider
{
	private float sldrVal;
	private final float max;
	private final float min;
	private final float defVal;
	private boolean changing;
	
	Slider(float max, float def)
	{
		this.min = (float) 0;
		this.max = max;
		
		sldrVal = (def - (float) 0) / (max - (float) 0);
				
		if (sldrVal > 1)
			sldrVal = 1;
		if (sldrVal < 0)
			sldrVal = 0;
		
		defVal = sldrVal;
	}
	
	public float getValue()
	{
		return min + sldrVal * (max - min);
	}
	
	public boolean isChanging()
	{
		return changing;
	}
	
	void draw(Graphics2D g, Color c, Color c2, int x, int y, int width)
	{
		if(ReplayViewer.clkHit || ReplayViewer.dragHit)
		{
			if(mouseR(x, y, width))
			{
				sldrVal = (ReplayViewer.mouseX - x - 4) / (float)(width - 7);
				
				if (sldrVal > 1)
					sldrVal = 1;
				if (sldrVal < 0)
					sldrVal = 0;
				
				changing = true;
			} else
			{
				changing = false;
			}
		} else
		{
			changing = false;
		}
		
		g.setColor(c2);
		g.fillRect(x + Math.round(defVal * (width - 7)) + 3, y - 2, 2, 17 + 4);
		g.setColor(c);
		g.fillRoundRect(x, y - 4 + 17 / 2, width, 8, 8, 8);
		g.setColor(c2);
		g.drawRoundRect(x, y - 4 + 17 / 2, width, 8, 8, 8);
		g.fillRoundRect(x + Math.round(sldrVal * (width - 7)), y, 8, 17, 5, 5);
	}
	
	private boolean mouseR(int x, int y, int width)
	{
        return !(ReplayViewer.mouseX < x || ReplayViewer.mouseY < y || ReplayViewer.mouseX > (x + width) || ReplayViewer.mouseY > (y + 17));
    }
}
