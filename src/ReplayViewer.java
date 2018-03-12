import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.MouseInputAdapter;

class ReplayViewer extends Canvas implements Runnable
{
	private static final long serialVersionUID = 355508539070062621L;
	private boolean running = true;
	private Thread thread;
	private Graphics2D g;
	private BufferStrategy bs;
	
	private FontMetrics fm;
	private Slider slider;
	private Slider saturation;
	private Slider bright;
	
	private Slider drawDist;
	static Slider drawGround;
	
	private float hue;
	private int gametype;
	private int scroll;
	
	private final Medium medium;
	private ContO[] aconto;
	private ContO[] aconto1;
	
	private int tframes;
	private int frame;
	private int nob;
	private int ofade;
	
	private int nplayers;
	private int carsel;
	private String[] plnames;
	private int[] maxmag;
	
	private int[][] pos;
	private int[][] check;
	private boolean end;
	private boolean endtype;
	
	private int[][] x;
	private int[][] y;
	private int[][] z;
	private int[][] xy;
	private int[][] zy;
	private int[][] xz;
	private int[][] wxz;
	private int[][] wzy;
	
	private float[][] power;
	private int[][] hitmag;
	
	private int[][] clear;
	private float[][] allrnp;
	
	private int[] clrad;
	private int[] msquash;
	private int[] flipy;
	private int[] squash;
	
	private String stagename;
	private int nlaps;
	private int nsp;
	
	private int[] checkx;
	private int[] checkz;
	private boolean multi;
	
	private int[][] fixtime;
	private int[][] wastd;
	private int[][][] dust;
	private int[][][] regx;
	private int[][][] regy;
	private int[][][] regz;
	private int[][][] sprk;
	private float[][] osmag;
	private float[][] regxf;
	private float[][] regyf;
	private float[][] regzf;
	private float[][][] sprkf; 
	private boolean[][] regyb;
	
	private int[] dustrk;
	private int[] fixtrk;
	private int[] xtrk;
	private int[] ytrk;
	private int[] ztrk;
	private int[] sprktrk;
	private int[] wastdtrk;
	
	private int[] fstr;
	private String[][] cname;
	private String[][] sentn;
	private int chatstr;
	
	private int relx;
	private int rely;
	private int relz;
	
	private double polar;
	private double azimuth;
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean rup;
	private boolean rdwn;
	private boolean fwd;
	private boolean bwd;
	private boolean rotxp;
	private boolean rotxn;
	private boolean cfovu;
	private boolean cfovd;
	private boolean framestep;
	private boolean addframe;
	private double fov = 90;
	static boolean iseenx;
	static boolean iseeny;
	static boolean going;
	private boolean layover;
	private boolean hide;
	private boolean precision;
	
	private boolean dist;
	private boolean time;
	
	static int mouseX;
	static int mouseY;
	
	private int selTab;
	static boolean clkHit;
	static boolean dragHit;
	private int view;
	private Color base;
	private float fps;
	
	static float sin_m_xz;
	static float cos_m_xz;
	static float sin_m_zy;
	static float cos_m_zy;
	static float sin_m_xy;
	static float cos_m_xy;
	
	private final KeyAdapter kadapter = new KeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent k)
		{
			int i = k.getKeyCode();
			
			if(i == KeyEvent.VK_Z)
				up = true;
			if(i == KeyEvent.VK_X)
				down = true;
			if(i == KeyEvent.VK_LEFT)
				left = true;
			if(i == KeyEvent.VK_RIGHT)
				right = true;
			if(i == KeyEvent.VK_UP)
				fwd = true;
			if(i == KeyEvent.VK_DOWN)
				bwd = true;
			if(i == KeyEvent.VK_A)
				rup = true;
			if(i == KeyEvent.VK_S)
				rdwn = true;
			if(i == KeyEvent.VK_Q)
				rotxp = true;
			if(i == KeyEvent.VK_W)
				rotxn = true;
			if(i > 0x30 && i < 0x31 + nplayers && i < 0x3A)
				carsel = pos[i - 0x31][0];
			if(i == 0x30 && nplayers > 9)
				carsel = pos[9][0];
			if(i > 0x60 && i < 0x61 + (nplayers - 10) && i < 0x6A)
				carsel = pos[i - 0x57][0];
			if(i == 0x60 && nplayers > 19)
				carsel = pos[19][0];
			if(i == KeyEvent.VK_D)
				addframe = !addframe;
			if(i == KeyEvent.VK_C)
				cfovu = true;
			if(i == KeyEvent.VK_V)
				cfovd = true;
			if(i == KeyEvent.VK_O)
				hide = !hide;
			if(i == KeyEvent.VK_P)
				precision = !precision;
		}
		@Override
		public void keyReleased(KeyEvent k)
		{
			int i = k.getKeyCode();
			
			if(i == KeyEvent.VK_Z)
				up = false;
			if(i == KeyEvent.VK_X)
				down = false;
			if(i == KeyEvent.VK_LEFT)
				left = false;
			if(i == KeyEvent.VK_RIGHT)
				right = false;
			if(i == KeyEvent.VK_UP)
				fwd = false;
			if(i == KeyEvent.VK_DOWN)
				bwd = false;
			if(i == KeyEvent.VK_A)
				rup = false;
			if(i == KeyEvent.VK_S)
				rdwn = false;
			if(i == KeyEvent.VK_Q)
				rotxp = false;
			if(i == KeyEvent.VK_W)
				rotxn = false;
			if(i == KeyEvent.VK_F)
			{
				framestep = true;
				addframe = false;
			}
			if(i == KeyEvent.VK_E)
				if(--view == -1)
					view = 9;
			if(i == KeyEvent.VK_R)
				if(++view == 10)
					view = 0;
			if(i == KeyEvent.VK_C)
				cfovu = false;
			if(i == KeyEvent.VK_V)
				cfovd = false;
		}
	};
	
	private final MouseInputAdapter madapter = new MouseInputAdapter()
	{
		public void mouseMoved(MouseEvent e)
		{
			mouseX = e.getX();
			mouseY = e.getY();
		}

		public void mouseClicked(MouseEvent e)
		{
			clkHit = true;
		}
		
		public void mouseDragged(MouseEvent e)
		{
			dragHit = true;
		}
		
		public void mouseReleased(MouseEvent e)
		{
			dragHit = false;
		}
	};
	
	private void start()
	{
		createBufferStrategy(3);
		bs = this.getBufferStrategy();
		
		/* Arial
		 * Idigital
		 * Trebuchet MS
		 * Tahoma
		 * Segoe UI
		 */
		
		Font f = new Font("Idigital", 1, 12);
		
		g = (Graphics2D)bs.getDrawGraphics();
		g.setFont(f);
		
		fm = getFontMetrics(f);
		
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop()
	{
		try
		{
			thread.join();
			g.dispose();
		}catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run() 
	{	
		long lastTime = System.nanoTime();
		final float numOfTicks = fps;
		float ns = 1000000000 / numOfTicks;
		double delta = 0;
		
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if (delta >= 1)
			{
				updateGameStateAndDrawToScreen();
				try
				{
					Thread.sleep(1);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				delta--;
			}
		}
		stop();
	}
	
	private void updateGameStateAndDrawToScreen()
	{
		going = ((addframe || framestep) && frame < tframes);
		
		if(going)
		{
			for(int i = 0; i < nplayers; i++)
				if(frame != 0 && clear[frame][i] != clear[frame - 1][i] && clear[frame - 1][i] != -1)
					check[i][clear[frame - 1][i]] = frame;
			
			for(int i = 0; i < nplayers; i++)
			{
				aconto1[i].x = x[frame][i];
				aconto1[i].y = y[frame][i];
				aconto1[i].z = z[frame][i];
				aconto1[i].xy = xy[frame][i];
				aconto1[i].zy = zy[frame][i];
				aconto1[i].xz = xz[frame][i];
				aconto1[i].wxz = wxz[frame][i];
				aconto1[i].wzy = wzy[frame][i];
				
				if(dustrk[i] < osmag[i].length)
				{
					while (dust[i][0][dustrk[i]] == frame)
					{
						aconto1[i].ust = dust[i][1][dustrk[i]];
						aconto1[i].sx[aconto1[i].ust] = dust[i][2][dustrk[i]];
						aconto1[i].sy[aconto1[i].ust] = dust[i][3][dustrk[i]];
						aconto1[i].sz[aconto1[i].ust] = dust[i][4][dustrk[i]];
						aconto1[i].scx[aconto1[i].ust] = dust[i][5][dustrk[i]];
						aconto1[i].scz[aconto1[i].ust] = dust[i][6][dustrk[i]];
						aconto1[i].osmag[aconto1[i].ust] = osmag[i][dustrk[i]];
						aconto1[i].stg[aconto1[i].ust] = 1;
						
						if(++dustrk[i] == osmag[i].length)
							break;
					}
				}
				
				if(fixtrk[i] < fixtime[i].length)
				{
					while (fixtime[i][fixtrk[i]] == frame)
					{
						aconto1[i].fix = true;
						
						if(++fixtrk[i] == fixtime[i].length)
							break;
					}
				}
				
				if(xtrk[i] < regxf[i].length)
				{
					while (regx[i][0][xtrk[i]] == frame)
					{
						regx(regx[i][1][xtrk[i]], regxf[i][xtrk[i]], aconto1[i], i);
						
						if(++xtrk[i] == regxf[i].length)
							break;
					}
				}
				
				if(ytrk[i] < regyf[i].length)
				{
					while (regy[i][0][ytrk[i]] == frame)
					{
						regy(regy[i][1][ytrk[i]], regyf[i][ytrk[i]], regyb[i][ytrk[i]], aconto1[i], i);
						
						if(++ytrk[i] == regyf[i].length)
							break;
					}
				}
				
				if(ztrk[i] < regzf[i].length)
				{
					while (regz[i][0][ztrk[i]] == frame)
					{
						regz(regz[i][1][ztrk[i]], regzf[i][ztrk[i]], aconto1[i], i);
						
						if(++ztrk[i] == regzf[i].length)
							break;
					}
				}
				
				if(sprktrk[i] < sprk[i][0].length)
				{
					while (sprk[i][0][sprktrk[i]] == frame)
					{
						aconto1[i].sprk = sprk[i][1][sprktrk[i]];
						aconto1[i].srx = sprk[i][2][sprktrk[i]];
						aconto1[i].sry = sprk[i][3][sprktrk[i]];
						aconto1[i].srz = sprk[i][4][sprktrk[i]];
						aconto1[i].rcx = sprkf[i][0][sprktrk[i]];
						aconto1[i].rcy = sprkf[i][1][sprktrk[i]];
						aconto1[i].rcz = sprkf[i][2][sprktrk[i]];
						
						if(++sprktrk[i] == sprk[i][0].length)
							break;
					}
				}
				
				if(wastdtrk[i] < wastd[i].length)
				{
					while (wastd[i][wastdtrk[i]] == frame)
					{
						for (int j = 0; j < aconto1[i].npl; j++)
						{
							if (aconto1[i].p[j].wz == 0 || aconto1[i].p[j].gr == -17 || aconto1[i].p[j].gr == -16)
							{
								aconto1[i].p[j].embos = 1;
							}
						}
						
						if(++wastdtrk[i] == wastd[i].length)
							break;
					}
				}
				
				
				for(int j = 0; j < aconto1[i].npl; j++)
				{
					if(aconto1[i].p[j].embos != 0)
					{
						if (aconto1[i].p[j].wz == 0 || aconto1[i].p[j].gr == -17 || aconto1[i].p[j].gr == -16)
						{
							if (aconto1[i].p[j].embos != 70)
							{
								aconto1[i].p[j].embos++;
							} else
							{
								aconto1[i].p[j].embos = 16;
							}
							
							aconto1[i].p[j].fmod = 1.0F;
							if (aconto1[i].p[j].embos <= 4)
							{
								aconto1[i].p[j].fmod = 1.0F + medium.random() / 5F;
							}
							if (aconto1[i].p[j].embos > 4 && aconto1[i].p[j].embos <= 7)
							{
								aconto1[i].p[j].fmod = 1.0F + medium.random() / 4F;
							}
							if (aconto1[i].p[j].embos > 7 && aconto1[i].p[j].embos <= 9)
							{
								aconto1[i].p[j].fmod = 1.0F + medium.random() / 3F;
								if (aconto1[i].p[j].hsb[2] > 0.69999999999999996D)
								{
									aconto1[i].p[j].hsb[2] = 0.7F;
								}
							}
							if (aconto1[i].p[j].embos > 9 && aconto1[i].p[j].embos <= 10)
							{
								aconto1[i].p[j].fmod = 1.0F + medium.random() / 2.0F;
								if (aconto1[i].p[j].hsb[2] > 0.59999999999999998D)
								{
									aconto1[i].p[j].hsb[2] = 0.6F;
								}
							}
							if (aconto1[i].p[j].embos > 10 && aconto1[i].p[j].embos <= 12)
							{
								aconto1[i].p[j].fmod = 1.0F + medium.random() / 1.0F;
								if (aconto1[i].p[j].hsb[2] > 0.5D)
								{
									aconto1[i].p[j].hsb[2] = 0.5F;
								}
							}
							if (aconto1[i].p[j].embos == 12)
							{
								aconto1[i].p[j].chip = 1;
								aconto1[i].p[j].ctmag = 2.0F;
								aconto1[i].p[j].bfase = -7;
							}
							if (aconto1[i].p[j].embos == 13)
							{
								aconto1[i].p[j].hsb[1] = 0.2F;
								aconto1[i].p[j].hsb[2] = 0.4F;
							}
							if (aconto1[i].p[j].embos == 16)
							{
								aconto1[i].p[j].pa = (int) (medium.random() * aconto1[i].p[j].n);
								for (aconto1[i].p[j].pb = (int) (medium.random() * aconto1[i].p[j].n); aconto1[i].p[j].pa == aconto1[i].p[j].pb; aconto1[i].p[j].pb = (int) (medium.random() * aconto1[i].p[j].n))
								{
								}
							}
						}
					}
					
					if (aconto1[i].p[j].chip == 1 && (medium.random() > 0.59999999999999998D || aconto1[i].p[j].bfase == 0))
					{
						aconto1[i].p[j].chip = 0;
						if (aconto1[i].p[j].bfase == 0 && aconto1[i].p[j].nocol)
						{
							aconto1[i].p[j].bfase = 1;
						}
					}
				}
				
				if (aconto1[i].fcnt > 6)
				{
					int j6 = aconto1[i].xz;
					int j7 = aconto1[i].xy;
					int l9 = aconto1[i].zy;
					
					if (multi)
						colorCar(aconto[i], i);
					
					aconto1[i] = new ContO(aconto[i], aconto1[i].x, aconto1[i].y, aconto1[i].z);
					aconto1[i].xz = j6;
					aconto1[i].xy = j7;
					aconto1[i].zy = l9;
				}
			}
			++frame;
		}
		
		medium.checkpoint = clear[frame > 0 ? frame - 1 : 0][carsel] % nsp;
		medium.lastcheck = clear[frame > 0 ? frame - 1 : 0][carsel] + 1 == nlaps * nsp;
		
		if(cfovu)
			fov += 0.5;
		if(cfovd)
			fov -= 0.5;
		
		if(fov > 180)
			fov = 180;
		if(fov < 0)
			fov = 0;
			
		medium.focus_point = (int)(medium.cx * Math.tan(fov * 0.5 * 0.0174532925199432957));
		
		if(!end)
		{
			for(int i = 0; i < nplayers; i++)
			{
				pos[i][0] = i; // Player
				pos[i][1] = Math.round(1000000 * (frame != 0 && hitmag[frame - 1][i] < maxmag[i] ? (float)hitmag[frame - 1][i] / maxmag[i] : 1));
				
				if(gametype != 2)
				{
					pos[i][1] = frame > 0 && clear[frame - 1][i] > 0 ? clear[frame - 1][i] : 0; // Checks Cleared
					
					// Distance until next check
					int cl = frame > 0 && clear[frame - 1][i] > -1 ? clear[frame - 1][i] % nsp : 0;
					
					long xpos = frame > 0 ? x[frame - 1][i] - checkx[cl] : aconto1[i].x - checkx[cl];
					long zpos = frame > 0 ? z[frame - 1][i] - checkz[cl] : aconto1[i].z - checkz[cl];
					
					pos[i][2] = (int)Math.round(Math.sqrt(xpos * xpos + zpos * zpos));
				}
				
				pos[i][4] = 0;
				if(frame != 0 && wastdtrk[i] > 0 && hitmag[frame - 1][i] >= maxmag[i])
					pos[i][4] = wastd[i][wastdtrk[i] - 1] - 1 - tframes;
				if(frame != 0 && clear[frame - 1][i] >= nlaps * nsp)
					pos[i][4] = tframes + 1 - check[i][clear[frame - 1][i] - 1];
			}
			
			int A;
			int B = 0;
			
			int C = nplayers;
			int D = -1;
			
			Arrays.sort(pos, (arg0, arg1) -> Integer.compare(arg1[4], arg0[4]));
			
			for (A = nplayers - 1; A > -1; A--)
			{
				if(pos[A][4] < 0)
					C = A;
				
				if(pos[A][4] > 0)
				{
					D = A;
					break;
				}
			}
					
			Arrays.sort(pos, D + 1, C, (arg0, arg1) -> Integer.compare(arg1[1], arg0[1]));
			
			if(gametype != 2)
			{
				java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
				
		        for (A = D + 1; A < C; A++) 
		        {
		            if (A == D + 1) 
		            {
		                list.add(D + 1);
		                B = pos[D + 1][1];
		            }
		            if (pos[A][1] != B) 
		            {
		                list.add(A);
		                B = pos[A][1];
		            }
		        }
		        list.add(C);
		
		        for (A = 0; A < list.size() - 1; A++)
		            Arrays.sort(pos, list.get(A), list.get(A + 1), (arg0, arg1) -> Integer.compare(arg0[2], arg1[2]));
		        
		        for(int i = 0; i < nplayers; i++)
		        {
		        	pos[i][3] = 0;
		        	
		        	for(int j = pos[0][1]; j > pos[i][1]; j--)
		        	{
		        		long xpos = checkx[j % nsp] - checkx[(j - 1) % nsp];
		        		long zpos = checkz[j % nsp] - checkz[(j - 1) % nsp];
		        		
		        		pos[i][3] += (int)Math.round(Math.sqrt(xpos * xpos + zpos * zpos));
		        	}
		        	
		        	pos[i][3] += pos[i][2];
		        	pos[i][3] -= pos[0][2];
		        }
		        
		        // The way positions should be
		        
		        Arrays.sort(pos, D + 1, C, (arg0, arg1) -> Integer.compare(arg0[3], arg1[3]));
			}
			
			if (C < 2 && nplayers > 1 || pos[0][1] == nsp * nlaps && endtype)
				end = true;
		}
		
        if(view == 0)
		{
			if(!precision)
			{
				if(left)
					medium.xz -= 2;
				if(right)
					medium.xz += 2;
				
				if(rup)
					medium.zy--;
				if(rdwn)
					medium.zy++;
				
				if(rotxp)
					medium.xy++;
				if(rotxn)
					medium.xy--;
				
				if(up)
					medium.y -= 100;
				if(down)
					medium.y += 100;
				
				if(fwd)
				{
					medium.z += 310 * (float)Math.cos(0.0174532925199432957 * medium.xz);
					medium.x += 310 * (float)Math.sin(0.0174532925199432957 * medium.xz);
				}
				if(bwd)
				{
					medium.z -= 310 * (float)Math.cos(0.0174532925199432957 * medium.xz);
					medium.x -= 310 * (float)Math.sin(0.0174532925199432957 * medium.xz);
				}
			} else
			{
				
				if(left)
					medium.x--;
				if(right)
					medium.x++;
				
				if(rup)
					medium.zy -= .01;
				if(rdwn)
					medium.zy += .01;
				
				if(rotxp)
					medium.xz -= .01;
				if(rotxn)
					medium.xz += .01;
				
				if(up)
					medium.y--;
				if(down)
					medium.y++;
				
				if(fwd)
					medium.z++;
				if(bwd)
					medium.z--;
			}
		} else
		{
			double[] vx = new double[3];
	    	double[] vy = new double[3];
	    	double[] vz = new double[3];
	    	
	    	if (view == 1)
	    	{
	    		/* Front Chase */
	    		
	    		/* Look-At Vector */
	    		vx[0] = 0;
		    	vy[0] = 0;
		    	vz[0] = 1;
		    	
	    	} else if (view == 2)
	    	{
	    		/* Rear Chase */
	    		
	    		vx[0] = 0;
		    	vy[0] = 0;
		    	vz[0] = -1;
		    	
	    	} else if (view == 3)
	    	{
	    		/* Left */
	    		
	    		vx[0] = -1;
		    	vy[0] = 0;
		    	vz[0] = 0;
	        	
	    	} else if (view == 4)
	    	{
	    		/* Right */
	    		
	    		vx[0] = 1;
		    	vy[0] = 0;
		    	vz[0] = 0;
		    	
	    	} else if (view == 5)
	    	{
	    		/* Helicopter */
	    		
	    		vx[0] = 0;
		    	vy[0] = 0;
		    	vz[0] = 1;
		    	
	    	} else if (view == 6)
	    	{
	    		/* Bumper */
	    		
	    		vx[0] = 0;
		    	vy[0] = 0;
		    	vz[0] = 1;

		    	/* Position Relative To Center */

		    	vx[2] = 0;
		    	vy[2] = -flipy[carsel] + 40;
		    	vz[2] = 0;
		    	
	    	} else if (view == 7)
	    	{
	    		/* Rear */
	    		
	    		vx[0] = 0;
		    	vy[0] = 0;
		    	vz[0] = -1;
		    	
		    	vx[2] = 0;
		    	vy[2] = -flipy[carsel] + 40;
		    	vz[2] = 0;
	        	
	    	} else if (view == 8)
	    	{
	    		/* Relative Cam */
	    		
	    		if(left)
	    			relx++;
	    		if(right)
	    			relx--;
	    		
	    		if(fwd)
	    			relz--;
	    		if(bwd)
	    			relz++;
	    		
	    		if(up)
	    			rely++;
	    		if(down)
	    			rely--;
	    		
	    		if(rup)
	    			polar--;
	    		if(rdwn)
	    			polar++;
	    		
	    		if(rotxp)
	    			azimuth++;
	    		if(rotxn)
	    			azimuth--;
	    		
	    		/*
	        	 * Control Direction of Relative Camera Car
	        	 */
	    		
	    		vx[0] = Math.cos(azimuth * 0.017453292519943296D) * Math.sin(polar * 0.017453292519943296D);
	        	vy[0] = Math.sin(azimuth * 0.017453292519943296D);
	        	vz[0] = Math.cos(azimuth * 0.017453292519943296D) * Math.cos(polar * 0.017453292519943296D);
	        	
	        	vx[2] = relx;
	        	vy[2] = -flipy[carsel] + 40 + rely;
	        	vz[2] = relz;
	    	} else
	    	{
	    		/* Spectator */
	    		
	    		vx[0] = 0;
		    	vy[0] = 0;
		    	vz[0] = 1;
	    	}
	    	
	    	/* Normal Vector */
	    	
	    	vx[1] = 0;
	    	vy[1] = -1;
	    	vz[1] = 0;
	    	
	    	/*
	    	 * Auto Lock on facing aconto[0]
	    	 * 
	    	vx[0] = -(aconto1[carsel].x - aconto1[0].x - relx);
	    	vy[0] = -(aconto1[carsel].y - aconto1[0].y - rely);
	    	vz[0] = -(aconto1[carsel].z - aconto1[0].z - relz);
	    	
	    	medium.rot(vx, vz, 0, 0, -aconto1[carsel].xz, 1);
	    	medium.rot(vy, vz, 0, 0, -aconto1[carsel].zy, 1);
	    	medium.rot(vx, vy, 0, 0, -aconto1[carsel].xy, 1);
	    	*/
	    	
	    	medium.rot(vx, vy, aconto1[carsel].xy);
	    	medium.rot(vy, vz, aconto1[carsel].zy);
	    	medium.rot(vx, vz, aconto1[carsel].xz);
	    	
	    	//if (view == 1)
	    	
	    	/*
	    	 * Matrix Conversion
	    	 *  
	    	double[] xaxis = {vy[1]*vz[0] - vz[1]*vy[0], vz[1]*vx[0] - vx[1]*vz[0], vx[1]*vy[0] - vy[1]*vx[0]};
	    	double[] yaxis = {vy[0]*xaxis[2] - vz[0]*xaxis[1], vz[0]*xaxis[0] - vx[0]*xaxis[2], vx[0]*xaxis[1] - vy[0]*xaxis[0]};

	    	double[] matrix = {xaxis[0], yaxis[0], vx[0],
	    						xaxis[1], yaxis[1], vy[0],
	    						xaxis[2], yaxis[2], vz[0]};
	    	*/
	    	
	    	double leng;
	    	
    		if (frame == 0)
    			leng = Math.atan2(x[frame + 1][carsel] - x[frame][carsel], z[frame + 1][carsel] - z[frame][carsel]);
    		else if (frame == tframes)
    			leng = Math.atan2(x[frame - 1][carsel] - x[frame - 2][carsel], z[frame - 1][carsel] - z[frame - 2][carsel]);
    		else
    			leng = Math.atan2(x[frame][carsel] - x[frame - 1][carsel], z[frame][carsel] - z[frame - 1][carsel]);
	    		
	    	double face = Math.atan2(vx[0], vz[0]);
    		double dot = Math.sin(face) * Math.sin(leng) + Math.cos(face) * Math.cos(leng);
    		
    		if(view == 3 || view == 4)
    			dot = vx[0] * Math.sin(face) + vz[0] * Math.cos(face);
    			
	    	if (view < 5)
	    	{
	    		medium.xz = (float)(face * 57.295779513082320877D);
		    	medium.zy = 10;
		    	medium.xy = 0;
		    	
		    	//System.out.println(dot + " " + vx[0] * Math.sin(face) + " " + vz[0] * Math.cos(face));
		    	/*
		    	if(view == 1)
			    	if(dot < 0)
			    		medium.xz += 180;
			    
		    	if(view == 2)
			    	if(dot > 0)
			    		medium.xz += 180;
		    	*/
		    	medium.x = Math.round(aconto1[carsel].x - medium.cx - 800 * (float)Math.sin(0.0174532925199432957 * medium.xz));
		        medium.z = Math.round(aconto1[carsel].z - medium.cz - 800 * (float)Math.cos(0.0174532925199432957 * medium.xz));
		        medium.y = aconto1[carsel].y - medium.cy - 250;
	    	} else if (view == 5)
	    	{
	    		medium.xz = (float)(face * 57.295779513082320877D);
	    		medium.zy = 90;
		    	medium.xy = 0;
		    	
		    	if(dot < 0)
		    		medium.xz += 180;
		    	
		    	medium.x = aconto1[carsel].x - medium.cx;
		        medium.z = aconto1[carsel].z - medium.cz;
		        medium.y = -medium.cy - 6000;
	    	} else if (view > 5 && view < 9){
		    	medium.x = (int) Math.round(aconto1[carsel].x - medium.cx - vx[2]);
		        medium.z = (int) Math.round(aconto1[carsel].z - medium.cz - vz[2]);
		        medium.y = (int) Math.round(aconto1[carsel].y - medium.cy - vy[2]);

		        /* Converts From Rotational Matrix above to Euler Angles */

		        medium.zy = (float)(Math.atan2(vy[0], Math.sqrt(vx[0] * vx[0] + vz[0] * vz[0])) * 57.295779513082320877D);
		        medium.xz = (float)(face * 57.295779513082320877D);
		        medium.xy = -(float)(Math.atan2(vz[1]*vx[0] - vx[1]*vz[0], vz[0]*vy[1]*vz[0] - vz[0]*vz[1]*vy[0] - vx[0]*vx[1]*vy[0] + vx[0]*vy[1]*vx[0]) * 57.295779513082320877D - 180);
		    } else
		    {
		    	if (Math.sqrt((long)(aconto1[carsel].z - medium.z) * (long)(aconto1[carsel].z - medium.z) + (long)(aconto1[carsel].x - medium.x - medium.cx) * (long)(aconto1[carsel].x - medium.x - medium.cx)) > 9500)
				{
		    		medium.xz = (float)(face * 57.295779513082320877D);
		    		
		    		if(dot < 0)
			    		medium.xz += 180;
		    		
		    		medium.y = Math.round(aconto1[carsel].y - medium.cy - 50 - 1100F * medium.random());
					medium.x = Math.round(aconto1[carsel].x - medium.cx + 400 * (float)Math.cos(0.0174532925199432957 * medium.xz) + 9000 * (float)Math.sin(0.0174532925199432957 * medium.xz));
					medium.z = Math.round(aconto1[carsel].z - medium.cz + 400 * (float)Math.sin(0.0174532925199432957 * medium.xz) + 9000 * (float)Math.cos(0.0174532925199432957 * medium.xz));
				}
				
		    	long vx1 = aconto1[carsel].x - medium.x - medium.cx;
		    	long vy1 = aconto1[carsel].y - medium.y - medium.cy;
		    	long vz1 = aconto1[carsel].z - medium.z - medium.cz;
		    	
		    	medium.xz = (float)(Math.atan2(vx1, vz1) * 57.295779513082320877D);
		    	medium.zy = (float)(Math.atan2(vy1, Math.sqrt(vx1 * vx1 + vz1 * vz1)) * 57.295779513082320877D);
		    }
		}
		
		sin_m_xz = (float)StrictMath.sin(0.0174532925199432957 * medium.xz);
		cos_m_xz = (float)StrictMath.cos(0.0174532925199432957 * medium.xz);
		sin_m_zy = (float)StrictMath.sin(0.0174532925199432957 * medium.zy);
		cos_m_zy = (float)StrictMath.cos(0.0174532925199432957 * medium.zy);
		sin_m_xy = (float)StrictMath.sin(0.0174532925199432957 * medium.xy);
		cos_m_xy = (float)StrictMath.cos(0.0174532925199432957 * medium.xy);
		
		/* Chip Values */
		
		for(int i = 0; i < nplayers; i++)
		{
			for(int j = 0; j < aconto1[i].npl; j++)
			{
				if (aconto1[i].p[j].chip != 0)
				{
					if (aconto1[i].p[j].chip == 1)
					{
						aconto1[i].p[j].cxz = aconto1[i].xz;
						aconto1[i].p[j].cxy = aconto1[i].xy;
						aconto1[i].p[j].czy = aconto1[i].zy;
						int i3 = (int) (medium.random() * aconto1[i].p[j].n);
						aconto1[i].p[j].cox[0] = aconto1[i].p[j].ox[i3];
						aconto1[i].p[j].coz[0] = aconto1[i].p[j].oz[i3];
						aconto1[i].p[j].coy[0] = aconto1[i].p[j].oy[i3];
						if (aconto1[i].p[j].ctmag > 3F)
						{
							aconto1[i].p[j].ctmag = 3F;
						}
						if (aconto1[i].p[j].ctmag < -3F)
						{
							aconto1[i].p[j].ctmag = -3F;
						}
						aconto1[i].p[j].cox[1] = (int) (aconto1[i].p[j].cox[0] + aconto1[i].p[j].ctmag * (10F - medium.random() * 20F));
						aconto1[i].p[j].cox[2] = (int) (aconto1[i].p[j].cox[0] + aconto1[i].p[j].ctmag * (10F - medium.random() * 20F));
						aconto1[i].p[j].coy[1] = (int) (aconto1[i].p[j].coy[0] + aconto1[i].p[j].ctmag * (10F - medium.random() * 20F));
						aconto1[i].p[j].coy[2] = (int) (aconto1[i].p[j].coy[0] + aconto1[i].p[j].ctmag * (10F - medium.random() * 20F));
						aconto1[i].p[j].coz[1] = (int) (aconto1[i].p[j].coz[0] + aconto1[i].p[j].ctmag * (10F - medium.random() * 20F));
						aconto1[i].p[j].coz[2] = (int) (aconto1[i].p[j].coz[0] + aconto1[i].p[j].ctmag * (10F - medium.random() * 20F));
						aconto1[i].p[j].dx = 0;
						aconto1[i].p[j].dy = 0;
						aconto1[i].p[j].dz = 0;
						if (aconto1[i].p[j].bfase != -7)
						{
							aconto1[i].p[j].vx = (int) (aconto1[i].p[j].ctmag * (30F - medium.random() * 60F));
							aconto1[i].p[j].vz = (int) (aconto1[i].p[j].ctmag * (30F - medium.random() * 60F));
							aconto1[i].p[j].vy = (int) (aconto1[i].p[j].ctmag * (30F - medium.random() * 60F));
						} else
						{
							aconto1[i].p[j].vx = (int) (aconto1[i].p[j].ctmag * (10F - medium.random() * 20F));
							aconto1[i].p[j].vz = (int) (aconto1[i].p[j].ctmag * (10F - medium.random() * 20F));
							aconto1[i].p[j].vy = (int) (aconto1[i].p[j].ctmag * (10F - medium.random() * 20F));
						}
						aconto1[i].p[j].chip = 2;
					}
					int ai4[] = new int[3];
					int ai6[] = new int[3];
					int ai8[] = new int[3];
					for (int k4 = 0; k4 < 3; k4++)
					{
						ai4[k4] = aconto1[i].p[j].cox[k4] + aconto1[i].x - medium.x;
						ai8[k4] = aconto1[i].p[j].coy[k4] + aconto1[i].y - medium.y;
						ai6[k4] = aconto1[i].p[j].coz[k4] + aconto1[i].z - medium.z;
					}
					aconto1[i].p[j].rot(ai4, ai8, aconto1[i].x - medium.x, aconto1[i].y - medium.y, aconto1[i].p[j].cxy, 3);
					aconto1[i].p[j].rot(ai8, ai6, aconto1[i].y - medium.y, aconto1[i].z - medium.z, aconto1[i].p[j].czy, 3);
					aconto1[i].p[j].rot(ai4, ai6, aconto1[i].x - medium.x, aconto1[i].z - medium.z, aconto1[i].p[j].cxz, 3);
					for (int l4 = 0; l4 < 3; l4++)
					{
						ai4[l4] += aconto1[i].p[j].dx;
						ai8[l4] += aconto1[i].p[j].dy;
						ai6[l4] += aconto1[i].p[j].dz;
					}
					if(going)
					{
						aconto1[i].p[j].dx += aconto1[i].p[j].vx;
						aconto1[i].p[j].dz += aconto1[i].p[j].vz;
						aconto1[i].p[j].dy += aconto1[i].p[j].vy;
						aconto1[i].p[j].vy += 7;
					}
					if (ai8[0] > medium.ground)
					{
						aconto1[i].p[j].chip = 19;
					}
					aconto1[i].p[j].rot(ai4, ai6, medium.cx, medium.cz, medium.xz, 3);
					aconto1[i].p[j].rot(ai8, ai6, medium.cy, medium.cz, medium.zy, 3);
					aconto1[i].p[j].rot(ai4, ai8, medium.cx, medium.cy, medium.xy, 3);
					int ai10[] = new int[3];
					int ai11[] = new int[3];
					for (int i6 = 0; i6 < 3; i6++)
					{
						ai10[i6] = aconto1[i].p[j].xs(ai4[i6], ai6[i6]);
						ai11[i6] = aconto1[i].p[j].ys(ai8[i6], ai6[i6]);
					}
					
					if(going)
						aconto1[i].p[j].chipc = (int) (medium.random() * 3F);
					
					aconto1[i].p[j].chipx1 = ai10[0];
					aconto1[i].p[j].chipx2 = ai10[1];
					aconto1[i].p[j].chipx3 = ai10[2];
					
					aconto1[i].p[j].chipy1 = ai11[0];
					aconto1[i].p[j].chipy2 = ai11[1];
					aconto1[i].p[j].chipy3 = ai11[2];
					
					if(going)
						aconto1[i].p[j].chip++;
					
					if (aconto1[i].p[j].chip == 20)
					{
						aconto1[i].p[j].chip = 0;
					}
				}
			}	
		}
		
		/* DRAW TO SCREEN */
		
		medium.d(g);
		
		double[][] ai1 = new double[nob][2];
		
		for (int k7 = 0; k7 < nob; k7++)
		{
			ai1[k7][0] = k7;
			ai1[k7][1] = Math.sqrt(Math.sqrt((double)(medium.x + medium.cx - aconto1[k7].x) * (double)(medium.x + medium.cx - aconto1[k7].x) + (double)(medium.z - aconto1[k7].z) * (double)(medium.z - aconto1[k7].z) + (double)(medium.y + medium.cy - aconto1[k7].y) * (double)(medium.y + medium.cy - aconto1[k7].y))) * (double)aconto1[k7].grounded;
		}
		
		Arrays.sort(ai1, (arg0, arg1) -> Double.compare(arg1[1], arg0[1]));
		
		for (int i14 = 0; i14 < nob; i14++)
			aconto1[(int)ai1[i14][0]].d(g);
		
		framestep = false;
        
		if(!hide || !layover)
		{
			if(!layover)
			{
				g.translate(0, medium.h);
				mouseY -= medium.h;
			}
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slider.getValue()));
			g.setColor(base.darker());
			g.fillRect(0, 0, medium.w, 100);
			g.setColor(base.darker().darker());
			g.drawString(stagename, medium.w - 14 - fm.stringWidth(stagename), 22);
			drawTabs(g, base.brighter(), base.darker(), base, new String[]{"Camera Info", "Player Info", "Match Info", "Instructions", "Options"}, medium.w - 14);
			g.setColor(base.brighter());
			
			if(--scroll <= 120 * -(gametype == 1 ? nplayers + 1 : nplayers))
				scroll = 0;
			
			if(selTab == 0)
			{
				if(view != 8)
				{
					g.drawString("Camera X : ", 17, 48);
					g.drawString("Camera Y : ", 17, 65);
					g.drawString("Camera Z : ", 17, 82);
					
					g.drawString("" + (medium.x + medium.cx), 110 - (fm.stringWidth("" + (medium.x + medium.cx)) / 2), 48);
					g.drawString("" + (medium.y + medium.cy), 110 - (fm.stringWidth("" + (medium.y + medium.cy)) / 2), 65);
					g.drawString("" + medium.z, 110 - (fm.stringWidth("" + medium.z) / 2), 82);
					
					g.drawString("Camera Pitch : ", 230 - fm.stringWidth("Camera Pitch : "), 48);
					g.drawString("Camera Yaw : ", 230 - fm.stringWidth("Camera Yaw : "), 65);
					g.drawString("Camera Roll : ", 230 - fm.stringWidth("Camera Roll : "), 82);
					
					g.drawString("" + medium.zy, 265 - (fm.stringWidth("" + medium.zy) / 2), 48);
					g.drawString("" + medium.xz, 265 - (fm.stringWidth("" + medium.xz) / 2), 65);
					g.drawString("" + medium.xy, 265 - (fm.stringWidth("" + medium.xy) / 2), 82);
				} else
				{
					g.drawString("Relative X : ", 17, 48);
					g.drawString("Relative Y : ", 17, 65);
					g.drawString("Relative Z : ", 17, 82);
					
					g.drawString("" + -relx, 110 - (fm.stringWidth("" + -relx) / 2), 48);
					g.drawString("" + rely, 110 - (fm.stringWidth("" + rely) / 2), 65);
					g.drawString("" + -relz, 110 - (fm.stringWidth("" + -relz) / 2), 82);
					
					g.drawString("Pitch/Azimuth : ", 230 - fm.stringWidth("Pitch/Azimuth : "), 48);
					g.drawString("Yaw/Polar : ", 230 - fm.stringWidth("Yaw/Polar : "), 65);
					g.drawString("Camera Roll : ", 230 - fm.stringWidth("Camera Roll : "), 82);
					
					g.drawString("" + azimuth, 265 - (fm.stringWidth("" + azimuth) / 2), 48);
					g.drawString("" + polar, 265 - (fm.stringWidth("" + polar) / 2), 65);
					g.drawString("" + medium.xy, 265 - (fm.stringWidth("" + medium.xy) / 2), 82);
				}
				
				if(view == 0)
				{
					g.drawString("Camera : Free Cam", medium.w - 17 - fm.stringWidth("Camera : Free Cam"), 48);
				} else if(view == 1)
				{
					g.drawString("Camera : Front Chase", medium.w - 17 - fm.stringWidth("Camera : Front Chase"), 48);
				} else if(view == 2)
				{
					g.drawString("Camera : Rear Chase", medium.w - 17 - fm.stringWidth("Camera : Rear Chase"), 48);
				} else if(view == 3)
				{
					g.drawString("Camera : Left", medium.w - 17 - fm.stringWidth("Camera : Left"), 48);
				} else if(view == 4)
				{
					g.drawString("Camera : Right", medium.w - 17 - fm.stringWidth("Camera : Right"), 48);
				} else if(view == 5)
				{
					g.drawString("Camera : Helicopter", medium.w - 17 - fm.stringWidth("Camera : Helicopter"), 48);
				} else if(view == 6)
				{
					g.drawString("Camera : Bumper", medium.w - 17 - fm.stringWidth("Camera : Bumper"), 48);
				} else if(view == 7)
				{
					g.drawString("Camera : Rear", medium.w - 17 - fm.stringWidth("Camera : Rear"), 48);
				} else if(view == 8)
				{
					g.drawString("Camera : Relative", medium.w - 17 - fm.stringWidth("Camera : Relative"), 48);
				}else
				{
					g.drawString("Camera : Spectator", medium.w - 17 - fm.stringWidth("Camera : Spectator"), 48);
				}
				
				g.drawString(plnames[carsel], medium.w - 17 - fm.stringWidth(plnames[carsel]), 65);
				g.drawString("Frame : " + frame + " / " + tframes, medium.w - 17 - fm.stringWidth("Frame : " + frame + " / " + tframes), 82);
				
				g.drawString("Poly : ", 340 - fm.stringWidth("Poly : "), 82);
				g.drawString("Dist : ", 340 - fm.stringWidth("Dist : "), 65);
				g.drawString("FOV : ", 340 - fm.stringWidth("FOV : "), 48);
				g.fillRect(340, 36, (int)Math.round(fov * 0.0055555555D * (medium.w - 340 - 140)), 14);
				
				g.setColor(base.darker());
				g.drawRect(340, 36, medium.w - 340 - 140, 14);
				
				g.drawString("" + fov, ((medium.w - 140 + 340) / 2) - (fm.stringWidth("" + fov) / 2), 48);
				
				drawDist.draw(g, base.brighter(), base.darker(), 340, 54, medium.w - 340 - 140);
				drawGround.draw(g, base.brighter(), base.darker(), 340, 71, medium.w - 340 - 140);
				
				if(drawDist.isChanging())
					medium.fadfrom((int)(ofade * drawDist.getValue()));
				
			} else if (selTab == 1)
			{
				String s = "1 " + plnames[0];
				int[] name_pos = new int[nplayers];
				name_pos[0] = 0;
				
				for(int i = 1; i < nplayers; i++)
				{
					s += " - " + (i + 1) + " " + plnames[i];
					name_pos[i] = fm.stringWidth(i + " " + plnames[i - 1] + " - ") + name_pos[i - 1];
				}
				
				int main = (medium.w - fm.stringWidth(s)) / 2;
				
				for(int i = 0; i < nplayers; i++)
				{
					if (i == carsel)
						g.setColor(base.darker());
					else
						g.setColor(base.brighter());
					
					g.drawString((i + 1) + " " + plnames[i], main + name_pos[i], 46);
					g.setColor(base.brighter());
					
					if (i != nplayers - 1)
						g.drawString(" - ", main + name_pos[i] + fm.stringWidth((i + 1) + " " + plnames[i]), 46);
				}
				
				g.drawString("Power : ", 79 - fm.stringWidth("Power : "), 65);
				g.drawString("Damage : ", 79 - fm.stringWidth("Damage : "), 82);
				
				g.fillRect(79, 53, (int)((frame != 0 ? (power[frame - 1][carsel] > 100 ? 1 : power[frame - 1][carsel] * 0.01)  : 1) * (medium.w - 79 - 17)), 14);
				g.fillRect(79, 71, (int)((frame != 0 && hitmag[frame - 1][carsel] < maxmag[carsel] ? (float)hitmag[frame - 1][carsel] / maxmag[carsel] : 1) * (medium.w - 79 - 17)), 14);
				
				g.setColor(base.darker());
				g.drawRect(79, 53, medium.w - 79 - 17, 14);
				g.drawRect(79, 71, medium.w - 79 - 17, 14);
				
				g.drawString(frame != 0 ? "" + power[frame - 1][carsel] : "100.0", ((medium.w - 17 + 79 - fm.stringWidth(frame != 0 ? "" + power[frame - 1][carsel] : "100.0")) / 2), 65);
				g.drawString(frame != 0 ? (hitmag[frame - 1][carsel] > maxmag[carsel] ? maxmag[carsel] + " / " + maxmag[carsel] : hitmag[frame - 1][carsel] + " / " + maxmag[carsel]) : maxmag[carsel] + " / " + maxmag[carsel],
						((medium.w - 17 + 79 - fm.stringWidth(frame != 0 ? (hitmag[frame - 1][carsel] > maxmag[carsel] ? maxmag[carsel] + " / " + maxmag[carsel] : hitmag[frame - 1][carsel] + " / " + maxmag[carsel]) : maxmag[carsel] + " / " + maxmag[carsel])) / 2), 83);
			} else if (selTab == 2)
			{
				Stroke str = g.getStroke();
				Shape orig = g.getClip();
				
				int centx = medium.w / 2 - 60 * nplayers;
				if(gametype == 1)
					centx -= 60;
				
				int chosen = 0;
				for(int i = 0; i < nplayers; i++)
				{
					if(pos[i][0] == carsel)
						chosen = i;
				}
				
				if (centx < 7)
				{
					g.setClip(7, 28, medium.w - 14, 64);
					
					for(int j = 0; j < 2; j++)
					{
						if(gametype == 1)
						{
							g.drawString("RUNNING ORDER", centx + 60 + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll - fm.stringWidth("RUNNING ORDER") / 2, 52);
							
							if(pos[0][1] / nsp + 1 > nlaps)
								g.drawString("--- FINISHED ---", centx + 60 + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll - fm.stringWidth("--- FINISHED ---") / 2, 76);
							else
								g.drawString("Lap " + (pos[0][1] / nsp + 1) + " of " + nlaps, centx + 60 + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll - fm.stringWidth("Lap " + (pos[0][1] / nsp + 1) + " of " + nlaps) / 2, 76);
						}
						
						for(int i = 0; i < nplayers; i++)
						{
							g.setColor(base.brighter());
							if(pos[i][0] == carsel)
								g.setColor(base.darker());

							g.drawString((i + 1) + " - " + plnames[pos[i][0]], centx + 60 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll - fm.stringWidth((i + 1) + " - " + plnames[pos[i][0]]) / 2, 46);
							
							if(gametype == 0)
							{
								if(frame == 0 || hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]])
									g.drawString("Lap : " + (pos[i][1] / nsp + 1), centx + 60 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll - fm.stringWidth("Lap : " + (pos[i][1] / nsp + 1)) / 2, 85);
							} else if (gametype == 1)
							{
								int jfs = 0;
								String str1 = "";
								
								if (pos[i][1] < pos[chosen][1] && pos[i][1] > 0)
									jfs = check[pos[i][0]][pos[i][1] - 1] - check[pos[chosen][0]][pos[i][1] - 1];
								else if (pos[i][1] >= pos[chosen][1] && pos[chosen][1] > 0)
									jfs = check[pos[i][0]][pos[chosen][1] - 1] - check[pos[chosen][0]][pos[chosen][1] - 1];
								
								if (dist && time)
									str1 = pos[i][0] == carsel ? (pos[i][1] > 0 ? check[carsel][pos[i][1] - 1] + "F": "---") + " / ---" : (jfs < 0 ? "- " + Math.abs(jfs) + "F / ": jfs > 0 ? "+ " + Math.abs(jfs) + "F / " : "--- / ") + (i > chosen ? "+ ": "- ") + Math.round(Math.abs(pos[i][3] - pos[chosen][3]) * .01) + "m";
								else if (dist)
									str1 = pos[i][0] == carsel ? "---" : (i > chosen ? "+ ": "- ") + Math.round(Math.abs(pos[i][3] - pos[chosen][3]) * .01) + "m";
								else if (time)
									str1 = pos[i][0] == carsel ? (pos[i][1] > 0 ? check[carsel][pos[i][1] - 1] + "F": "---") : (jfs < 0 ? "- " + Math.abs(jfs) + "F": jfs > 0 ? "+ " + Math.abs(jfs) + "F" : "---");
								
								if(frame == 0 || hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]])
									g.drawString(str1, centx + 60 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll - fm.stringWidth(str1) / 2, 85);
							} else
							{
								if(frame == 0 || hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]])
									g.drawString(pos[i][1] * 0.0001F + "%", centx + 60 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll - fm.stringWidth((pos[i][1] * 0.0001F + "%")) / 2, 85);
							}
							
							g.setColor(base.brighter());
							
							if(frame == 0 || hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]])
							{	
								g.fillRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll, 50, Math.round(frame != 0 ? power[frame - 1][pos[i][0]] : 100), 10);
								g.fillRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll, 60, Math.round(100 * (frame != 0 && hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]] ? (float)hitmag[frame - 1][pos[i][0]] / maxmag[pos[i][0]] : 1)), 10);
								
								g.setColor(base.darker());
								g.drawRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll, 50, 100, 10);
								g.drawRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll, 60, 100, 10);
							} else
							{
								g.fillRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll, 50, 100, 20);
								g.setColor(base.darker());
								g.drawRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll, 50, 100, 20);
								g.drawString("W A S T E D", centx + 60 + 120 * (gametype == 1 ? i + 1 : i) + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll - (fm.stringWidth("W A S T E D")) / 2, 65);
							}
						}
						
						g.setStroke(new BasicStroke(2.0F));
						g.setColor(base.darker().darker());
						
						if(gametype == 1)
							g.drawRect(centx + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll, 28, 120, 64);
						else
							g.drawLine(centx + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll, 28, centx + 120 * (gametype == 1 ? nplayers + 1 : nplayers) * j + scroll, 92);
						
						if(j == 0)
							g.setStroke(str);
					}
				} else
				{
					g.setClip(centx, 28, 120 * (gametype == 1 ? nplayers + 1 : nplayers), 64);
					
					if(gametype == 1)
					{
						g.drawString("RUNNING ORDER", centx + 60 - fm.stringWidth("RUNNING ORDER") / 2, 52);
						
						if(pos[0][1] / nsp + 1 > nlaps)
							g.drawString("--- FINISHED ---", centx + 60 - fm.stringWidth("--- FINISHED ---") / 2, 76);
						else
							g.drawString("Lap " + (pos[0][1] / nsp + 1) + " of " + nlaps, centx + 60 - fm.stringWidth("Lap " + (pos[0][1] / nsp + 1) + " of " + nlaps) / 2, 76);
					}
					
					for(int i = 0; i < nplayers; i++)
					{
						g.setColor(base.brighter());
						if(pos[i][0] == carsel)
							g.setColor(base.darker());

						g.drawString((i + 1) + " - " + plnames[pos[i][0]], centx + 60 + 120 * (gametype == 1 ? i + 1 : i) - fm.stringWidth((i + 1) + " - " + plnames[pos[i][0]]) / 2, 46);
						
						if(gametype == 0)
						{
							if(frame == 0 || hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]])
								g.drawString("Lap : " + (pos[i][1] / nsp + 1), centx + 60 + 120 * (gametype == 1 ? i + 1 : i) - fm.stringWidth("Lap : " + (pos[i][1] / nsp + 1)) / 2, 85);
						} else if (gametype == 1)
						{
							int jfs = 0;
							String str1 = "";
							
							if (pos[i][1] < pos[chosen][1] && pos[i][1] > 0)
								jfs = check[pos[i][0]][pos[i][1] - 1] - check[pos[chosen][0]][pos[i][1] - 1];
							else if (pos[i][1] >= pos[chosen][1] && pos[chosen][1] > 0)
								jfs = check[pos[i][0]][pos[chosen][1] - 1] - check[pos[chosen][0]][pos[chosen][1] - 1];
							
							if (dist && time)
								str1 = pos[i][0] == carsel ? (pos[i][1] > 0 ? check[carsel][pos[i][1] - 1] + "F": "---") + " / ---" : (jfs < 0 ? "- " + Math.abs(jfs) + "F / ": jfs > 0 ? "+ " + Math.abs(jfs) + "F / " : "--- / ") + (i > chosen ? "+ ": "- ") + Math.round(Math.abs(pos[i][3] - pos[chosen][3]) * .01) + "m";
							else if (dist)
								str1 = pos[i][0] == carsel ? "---" : (i > chosen ? "+ ": "- ") + Math.round(Math.abs(pos[i][3] - pos[chosen][3]) * .01) + "m";
							else if (time)
								str1 = pos[i][0] == carsel ? (pos[i][1] > 0 ? check[carsel][pos[i][1] - 1] + "F": "---") : (jfs < 0 ? "- " + Math.abs(jfs) + "F": jfs > 0 ? "+ " + Math.abs(jfs) + "F" : "---");
							
							if(frame == 0 || hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]])
								g.drawString(str1, centx + 60 + 120 * (gametype == 1 ? i + 1 : i) - fm.stringWidth(str1) / 2, 85);
						} else
						{
							if(frame == 0 || hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]])
								g.drawString(pos[i][1] * 0.0001F + "%", centx + 60 + 120 * (gametype == 1 ? i + 1 : i) - fm.stringWidth((pos[i][1] * 0.0001F + "%")) / 2, 85);
						}
						
						g.setColor(base.brighter());
						
						if(frame == 0 || hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]])
						{	
							g.fillRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i), 50, Math.round(frame != 0 ? power[frame - 1][pos[i][0]] : 100), 10);
							g.fillRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i), 60, Math.round(100 * (frame != 0 && hitmag[frame - 1][pos[i][0]] < maxmag[pos[i][0]] ? (float)hitmag[frame - 1][pos[i][0]] / maxmag[pos[i][0]] : 1)), 10);
							
							g.setColor(base.darker());
							g.drawRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i), 50, 100, 10);
							g.drawRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i), 60, 100, 10);
						} else
						{
							g.fillRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i), 50, 100, 20);
							g.setColor(base.darker());
							g.drawRect(centx + 10 + 120 * (gametype == 1 ? i + 1 : i), 50, 100, 20);
							g.drawString("W A S T E D", centx + 60 + 120 * (gametype == 1 ? i + 1 : i) - (fm.stringWidth("W A S T E D")) / 2, 65);
						}
					}
					
					g.setStroke(new BasicStroke(2.0F));
					g.setColor(base.darker().darker());
					
					if(gametype == 1 && centx + 120 >= 7)
						g.drawLine(centx + 120, 28, centx + 120, 92);
				}
				
				g.setClip(orig);
				
				if (centx < 7)
					g.drawRect(7, 28, medium.w - 14, 64);
				else
					g.drawRect(centx, 28, 120 * (gametype == 1 ? nplayers + 1 : nplayers), 64);
				
				g.setStroke(str);
				
			} else if (selTab == 3)
			{
				g.drawString("Play/Pause - D, FrameStep - F, Change Camera - E/R, Change Player - <1 - 0>, Change FOV - C/V", (medium.w - fm.stringWidth("Play/Pause - D, FrameStep - F, Change Camera - E/R, Change Player - <1 - 0>, Change FOV - C/V")) / 2, 48);
				g.drawString("Change Pitch - A/S, Camera Up and Down - Z/X, Hide/Show GUI - O", (medium.w - fm.stringWidth("Change Pitch - A/S, Camera Up and Down - Z/X, Hide/Show GUI - O")) / 2, 65);
				g.drawString("On Free Cam : <+>Fwd/Yaw <Q/W>Roll              On Relative Cam : <+>Z/X <Q/W>Yaw", (medium.w - fm.stringWidth("On Free Cam : <+>Fwd/Yaw <Q/W>Roll              On Relative Cam : <+>Z/X <Q/W>Yaw")) / 2, 82);
			} else
			{
				g.drawString("Transparency : ", 120 - fm.stringWidth("Transparency : "), 48);
				slider.draw(g, base.brighter(), base.darker(), 120, 37, medium.w - 120 - 17);
				g.setColor(base.brighter());
				g.drawString("Saturation : ", 120 - fm.stringWidth("Saturation : "), 65);
				saturation.draw(g, base.brighter(), base.darker(), 120, 54, medium.w - 120 - 17);
				g.setColor(base.brighter());
				g.drawString("Brightness : ", 120 - fm.stringWidth("Brightness : "), 82);
				bright.draw(g, base.brighter(), base.darker(), 120, 71, medium.w - 120 - 17);
				
				if (saturation.isChanging() || bright.isChanging())
					base = new Color(Color.HSBtoRGB(hue, saturation.getValue(), bright.getValue()));
			}
			
			if (multi)
			{
				if(chatstr < fstr.length)
				{
					while (fstr[chatstr] == frame - 1)
					{
						if(++chatstr == fstr.length)
							break;
					}
				}
				
				g.setColor(base.darker());
				g.fillRect(0, 100, medium.w, 27);
				g.setColor(base.brighter());
				g.fillRect(7, 100, medium.w - 14, 20);
				Graphics2D g2 = (Graphics2D)g.create();
				
				g2.setStroke(new BasicStroke(2.0F));
				g2.setColor(base.darker().darker());
				g2.drawRect(7, 100, medium.w - 14, 20);
				g2.setClip(14, 100, medium.w - 28, 20);
				int x = 0;
				
				if(chatstr != 0)
				{
					for (int i = 6; i > -1; i--)
					{
						g2.setColor(base.darker());
						g2.drawString(cname[chatstr - 1][i] + " : ", 14 + x, 115);
						g2.setColor(base);
						g2.drawString(sentn[chatstr - 1][i] + "       ", 14 + fm.stringWidth(cname[chatstr - 1][i] + " : ") + x, 115);
						
						x += fm.stringWidth(cname[chatstr - 1][i] + " : " + sentn[chatstr - 1][i] + "       ");
					}
				}
				
				g2.dispose();
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			
			if(!layover)
			{
				g.translate(0, -medium.h);
				mouseY += medium.h;
			}
		}
		bs.show();
	}
	
	
	private void drawTabs(Graphics2D g, Color c, Color c2, Color c3, String[] titles, int width)
	{
		int[] length = new int[titles.length];
		int exp = 0;
		
		Color c4 = new Color((int)(c.getRed() * .8), (int)(c.getGreen() * .8), (int)(c.getBlue() * .8));
		Color c5 = new Color((int)(c2.getRed() * .8), (int)(c2.getGreen() * .8), (int)(c2.getBlue() * .8));
		
		Color c6 = new Color((int)(c.getRed() * .6), (int)(c.getGreen() * .6), (int)(c.getBlue() * .6));
		Color c7 = new Color((int)(c2.getRed() * .6), (int)(c2.getGreen() * .6), (int)(c2.getBlue() * .6));
		
		for(int i = 0; i < length.length; i++)
		{
			length[i] = fm.stringWidth(titles[i]);

			if(mouseR(7 + exp, length[i] + 12) && clkHit)
			{
			    selTab = i;
			    clkHit = false;
			}
			
			if(i == selTab)
			{
				g.setColor(c);
				g.fillPolygon(new int[]{7 + exp, 7 + exp, 7 + length[i] + 12 + exp, 7 + length[i] + 20 + exp}, new int[]{7 + 21, 7, 7, 7 + 21}, 4);
				g.setColor(c2);
				g.drawString(titles[i], 7 + 7 + exp, 7 + 15);
			}else if(mouseR(7 + exp, length[i] + 12))
			{
				g.setColor(c4);
				g.fillPolygon(new int[]{7 + exp, 7 + exp, 7 + length[i] + 12 + exp, 7 + length[i] + 20 + exp}, new int[]{7 + 21, 7, 7, 7 + 21}, 4);
				g.setColor(c5);
				g.drawString(titles[i], 7 + 7 + exp, 7 + 15);
			}else
			{
				g.setColor(c6);
				g.fillPolygon(new int[]{7 + exp, 7 + exp, 7 + length[i] + 12 + exp, 7 + length[i] + 20 + exp}, new int[]{7 + 21, 7, 7, 7 + 21}, 4);
				g.setColor(c7);
				g.drawString(titles[i], 7 + 7 + exp, 7 + 15);
			}
			exp += (length[i] + 20);
		}
		g.setColor(c3);
		g.fillRect(7, 7 + 21, (width > exp ? width : exp), 65);
	}
	
	private boolean mouseR(int x, int width)
	{
		return !(mouseX < x || mouseY < 7 || mouseX > (x + width) || mouseY > (7 + 21));
	}
	
	@SuppressWarnings("unchecked")
	private ReplayViewer(String filepath, int w, int h) throws Exception
	{
		medium = new Medium();
		medium.w = w;
		medium.h = h;
		
		medium.cx = w / 2;
		medium.cy = h / 2;
		
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filepath)));
		ofade = dis.readInt();
		medium.fadfrom(ofade);
		
		slider = new Slider(1, 1);
		
		medium.snap[0] = dis.readInt();
		medium.snap[1] = dis.readInt();
		medium.snap[2] = dis.readInt();
		
		medium.csky[0] = dis.readInt();
		medium.csky[1] = dis.readInt();
		medium.csky[2] = dis.readInt();
		
		medium.cfade[0] = dis.readInt();
		medium.cfade[1] = dis.readInt();
		medium.cfade[2] = dis.readInt();
		
		medium.clds[0] = dis.readInt();
		medium.clds[1] = dis.readInt();
		medium.clds[2] = dis.readInt();
		
		medium.cldd[0] = dis.readInt();
		medium.cldd[1] = dis.readInt();
		medium.cldd[2] = dis.readInt();
		medium.cldd[3] = dis.readInt();
		medium.cldd[4] = dis.readInt();
		
		medium.cpol[0] = dis.readInt();
		medium.cpol[1] = dis.readInt();
		medium.cpol[2] = dis.readInt();
		
		medium.cgrnd[0] = dis.readInt();
		medium.cgrnd[1] = dis.readInt();
		medium.cgrnd[2] = dis.readInt();
		
		medium.crgrnd[0] = dis.readInt();
		medium.crgrnd[1] = dis.readInt();
		medium.crgrnd[2] = dis.readInt();
		
		medium.sgpx = dis.readInt();
		medium.sgpz = dis.readInt();
		medium.nrw = dis.readInt();
		medium.ncl = dis.readInt();
		
		medium.cgpx = new int[medium.nrw * medium.ncl];
		medium.cgpz = new int[medium.nrw * medium.ncl];
		medium.pmx = new int[medium.nrw * medium.ncl];
		medium.pcv = new float[medium.nrw * medium.ncl];
		
		medium.ogpx = new int[medium.nrw * medium.ncl][8];
		medium.ogpz = new int[medium.nrw * medium.ncl][8];
		medium.pvr = new float[medium.nrw * medium.ncl][8];
		
		for(int i = 0; i < medium.cgpx.length; i++)
			medium.cgpx[i] = dis.readInt();
		for(int i = 0; i < medium.cgpz.length; i++)
			medium.cgpz[i] = dis.readInt();
		for(int i = 0; i < medium.pmx.length; i++)
			medium.pmx[i] = dis.readInt();
		for(int i = 0; i < medium.pcv.length; i++)
			medium.pcv[i] = dis.readFloat();
		
		for(int i = 0; i < medium.ogpx.length; i++)
			for(int j = 0; j < 8; j++)
				medium.ogpx[i][j] = dis.readInt();
		
		for(int i = 0; i < medium.ogpz.length; i++)
			for(int j = 0; j < 8; j++)
				medium.ogpz[i][j] = dis.readInt();
		
		for(int i = 0; i < medium.pvr.length; i++)
			for(int j = 0; j < 8; j++)
				medium.pvr[i][j] = dis.readFloat();
		
		medium.noc = dis.readInt();
		medium.fogd = dis.readInt();
		
		medium.clx = new int[medium.noc];
		medium.clz = new int[medium.noc];
		medium.cmx = new int[medium.noc];
		
		medium.clax = new int[medium.noc][3][12];
		medium.clay = new int[medium.noc][3][12];
		medium.claz = new int[medium.noc][3][12];
		medium.clc = new int[medium.noc][2][6][3];
		
		for(int i = 0; i < medium.noc; i++)
			medium.clx[i] = dis.readInt();
		for(int i = 0; i < medium.noc; i++)
			medium.clz[i] = dis.readInt();
		for(int i = 0; i < medium.noc; i++)
			medium.cmx[i] = dis.readInt();
		
		for(int i = 0; i < medium.noc; i++)
			for(int j = 0; j < 3; j++)
				for(int k = 0; k < 12; k++)
					medium.clax[i][j][k] = dis.readInt();
		
		for(int i = 0; i < medium.noc; i++)
			for(int j = 0; j < 3; j++)
				for(int k = 0; k < 12; k++)
					medium.clay[i][j][k] = dis.readInt();
		
		for(int i = 0; i < medium.noc; i++)
			for(int j = 0; j < 3; j++)
				for(int k = 0; k < 12; k++)
					medium.claz[i][j][k] = dis.readInt();
		
		for(int i = 0; i < medium.noc; i++)
			for(int j = 0; j < 2; j++)
				for(int k = 0; k < 6; k++)
					for(int l = 0; l < 3; l++)
						medium.clc[i][j][k][l] = dis.readInt();
		
		medium.nmt = dis.readInt();
		
		medium.mrd = new int[medium.nmt];
		medium.nmv = new int[medium.nmt];
		
		medium.mtx = new int[medium.nmt][];
		medium.mty = new int[medium.nmt][];
		medium.mtz = new int[medium.nmt][];
		medium.mtc = new int[medium.nmt][][];
		
		for(int i = 0; i < medium.nmt; i++)
			medium.mrd[i] = dis.readInt();
		for(int i = 0; i < medium.nmt; i++)
			medium.nmv[i] = dis.readInt();
		
		for (int l1 = 0; l1 < medium.nmt; l1++)
		{
			medium.mtx[l1] = new int[medium.nmv[l1] * 2];
			medium.mty[l1] = new int[medium.nmv[l1] * 2];
			medium.mtz[l1] = new int[medium.nmv[l1] * 2];
			medium.mtc[l1] = new int[medium.nmv[l1]][3];
		}
		
		for(int i = 0; i < medium.nmt; i++)
			for(int j = 0; j < medium.nmv[i] * 2; j++)
				medium.mtx[i][j] = dis.readInt();
		
		for(int i = 0; i < medium.nmt; i++)
			for(int j = 0; j < medium.nmv[i] * 2; j++)
				medium.mty[i][j] = dis.readInt();
		
		for(int i = 0; i < medium.nmt; i++)
			for(int j = 0; j < medium.nmv[i] * 2; j++)
				medium.mtz[i][j] = dis.readInt();
		
		for(int i = 0; i < medium.nmt; i++)
			for(int j = 0; j < medium.nmv[i]; j++)
				for(int k = 0; k < 3; k++)
					medium.mtc[i][j][k] = dis.readInt();
		
		medium.lightson = dis.readBoolean();
		
		if(medium.lightson)
		{
			medium.nst = dis.readInt();
			
			medium.stx = new int[medium.nst];
			medium.stz = new int[medium.nst];
			medium.stc = new int[medium.nst][2][3];
			medium.bst = new boolean[medium.nst];
			medium.twn = new int[medium.nst];
			
			for(int i = 0; i < medium.nst; i++)
				medium.stx[i] = dis.readInt();
			for(int i = 0; i < medium.nst; i++)
				medium.stz[i] = dis.readInt();
			
			for(int i = 0; i < medium.nst; i++)
				for(int j = 0; j < 2; j++)
					for(int k = 0; k < 3; k++)
						medium.stc[i][j][k] = dis.readInt();
			
			for(int i = 0; i < medium.nst; i++)
				medium.bst[i] = dis.readBoolean();
			for(int i = 0; i < medium.nst; i++)
				medium.twn[i] = dis.readInt();
		}
		
		medium.x = -medium.cx;
		medium.y = -700 - medium.cy;
		medium.z = -3000;
		
		relx = 0;
		rely = 0;
		relz = 0;
		
		polar = 0;
		azimuth = 0;
		
		addframe = false;
		framestep = false;
		
		frame = 0;
		tframes = dis.readInt();
		nplayers = dis.readInt();
		multi = dis.readBoolean();
		
		plnames = new String[nplayers];
		maxmag = new int[nplayers];
		clrad = new int[nplayers];
		msquash = new int[nplayers];
		flipy = new int[nplayers];
		squash = new int[nplayers];
		pos = new int[nplayers][5];
		
		for(int i = 0; i < nplayers; i++)
			plnames[i] = dis.readUTF();
		
		if (multi)
		{
			allrnp = new float[nplayers][6];
			
			for(int i = 0; i < nplayers; i++)
				for(int j = 0; j < 6; j++)
					allrnp[i][j] = dis.readFloat();
			
			int i = dis.readInt();
			fstr = new int[i];
			cname = new String[i][7];
			sentn = new String[i][7];
			
			for(int j = 0; j < i; j++)
			{
				fstr[j] = dis.readInt();
				for(int k = 0; k < 7; k++)
				{
					cname[j][k] = dis.readUTF();
					sentn[j][k] = dis.readUTF();
				}
			}
		}
		
		for(int i = 0; i < nplayers; i++)
			maxmag[i] = dis.readInt();
		for(int i = 0; i < nplayers; i++)
			clrad[i] = dis.readInt();
		for(int i = 0; i < nplayers; i++)
			msquash[i] = dis.readInt();
		for(int i = 0; i < nplayers; i++)
			flipy[i] = dis.readInt();
		
		stagename = dis.readUTF();
		nlaps = dis.readInt();
		nsp = dis.readInt();
		
		check = new int[nplayers][nlaps * nsp];
		checkx = new int[nsp];
		checkz = new int[nsp];
		
		medium.nochekflk = dis.readBoolean();
		
		x = new int[tframes][nplayers];
		y = new int[tframes][nplayers];
		z = new int[tframes][nplayers];
		xy = new int[tframes][nplayers];
		zy = new int[tframes][nplayers];
		xz = new int[tframes][nplayers];
		wxz = new int[tframes][nplayers];
		wzy = new int[tframes][nplayers];
		
		power = new float[tframes][nplayers];
		hitmag = new int[tframes][nplayers];
		clear = new int[tframes][nplayers];
		
		for(int lop = 0; lop < tframes; lop++)
		{
			for(int lop_i = 0; lop_i < nplayers; lop_i++)
			{
				x[lop][lop_i] = dis.readInt();
				y[lop][lop_i] = dis.readInt();
				z[lop][lop_i] = dis.readInt();
				xy[lop][lop_i] = dis.readInt();
				zy[lop][lop_i] = dis.readInt();
				xz[lop][lop_i] = dis.readInt();
				wxz[lop][lop_i] = dis.readInt();
				wzy[lop][lop_i] = dis.readInt();
				
				power[lop][lop_i] = dis.readFloat();
				hitmag[lop][lop_i] = dis.readInt();
				clear[lop][lop_i] = dis.readInt();
			}
		}
		
		dust = new int[nplayers][][];
		osmag = new float[nplayers][];
		fixtime = new int[nplayers][];
		regx = new int[nplayers][][];
		regxf = new float[nplayers][];
		regy = new int[nplayers][][];
		regyf = new float[nplayers][];
		regyb = new boolean[nplayers][];
		regz = new int[nplayers][][];
		regzf = new float[nplayers][];
		sprk = new int[nplayers][][];
		sprkf = new float[nplayers][][];
		wastd = new int[nplayers][];
		
		dustrk = new int[nplayers];
		fixtrk = new int[nplayers];
		xtrk = new int[nplayers];
		ytrk = new int[nplayers];
		ztrk = new int[nplayers];
		sprktrk = new int[nplayers];
		wastdtrk = new int[nplayers];
		
		for(int lop = 0; lop < nplayers; lop++)
		{
			Arrays.fill(check[lop], -1);
			
			int a = dis.readInt() / 32;
			int b = dis.readInt() / 4;
			int c = dis.readInt() / 12;
			int d = dis.readInt() / 13;
			int e = dis.readInt() / 12;
			int f = dis.readInt() / 32;
			int g = dis.readInt() / 4;
			
			dust[lop] = new int[7][a];
			osmag[lop] = new float[a];
			fixtime[lop] = new int[b];
			regx[lop] = new int[2][c];
			regxf[lop] = new float[c];
			regy[lop] = new int[2][d];
			regyf[lop] = new float[d];
			regyb[lop] = new boolean[d];
			regz[lop] = new int[2][e];
			regzf[lop] = new float[e];
			sprk[lop] = new int[5][f];
			sprkf[lop] = new float[3][f];
			wastd[lop] = new int[g];
			
			for (int i = 0; i < a; i++)
			{
				dust[lop][0][i] = dis.readInt();
				dust[lop][1][i] = dis.readInt();
				dust[lop][2][i] = dis.readInt();
				dust[lop][3][i] = dis.readInt();
				dust[lop][4][i] = dis.readInt();
				dust[lop][5][i] = dis.readInt();
				dust[lop][6][i] = dis.readInt();
				osmag[lop][i] = dis.readFloat();
			}
			
			for (int i = 0; i < b; i++)
				fixtime[lop][i] = dis.readInt();
			
			for (int i = 0; i < c; i++)
			{
				regx[lop][0][i] = dis.readInt();
				regx[lop][1][i] = dis.readInt();
				regxf[lop][i] = dis.readFloat();
			}
			
			for (int i = 0; i < d; i++)
			{
				regy[lop][0][i] = dis.readInt();
				regy[lop][1][i] = dis.readInt();
				regyf[lop][i] = dis.readFloat();
				regyb[lop][i] = dis.readBoolean();
			}
			
			for (int i = 0; i < e; i++)
			{
				regz[lop][0][i] = dis.readInt();
				regz[lop][1][i] = dis.readInt();
				regzf[lop][i] = dis.readFloat();
			}
			
			for (int i = 0; i < f; i++)
			{
				sprk[lop][0][i] = dis.readInt();
				sprk[lop][1][i] = dis.readInt();
				sprk[lop][2][i] = dis.readInt();
				sprk[lop][3][i] = dis.readInt();
				sprk[lop][4][i] = dis.readInt();
				sprkf[lop][0][i] = dis.readFloat();
				sprkf[lop][1][i] = dis.readFloat();
				sprkf[lop][2][i] = dis.readFloat();
			}
			
			for (int i = 0; i < g; i++)
				wastd[lop][i] = dis.readInt();
		}
		
		byte[] data_cars = new byte[dis.readInt()];
		byte[] data_track = new byte[dis.readInt()];
		byte[] data_trackers = new byte[dis.readInt()];
		
		dis.readFully(data_cars);
		dis.readFully(data_track);
		dis.readFully(data_trackers);
		
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data_cars));
		ArrayList<ContO> temp = (ArrayList<ContO>) ois.readObject();
		aconto = temp.toArray(new ContO[temp.size()]);
		ois.close();
		
		ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(data_track));
		ArrayList<ContO> temp_2 = (ArrayList<ContO>) ois2.readObject();
		aconto1 = temp_2.toArray(new ContO[temp_2.size()]);
		nob = aconto1.length;
		ois2.close();
		
		ObjectInputStream ois3 = new ObjectInputStream(new ByteArrayInputStream(data_trackers));
		Trackers trackers = (Trackers) ois3.readObject();
		ois3.close();
		
		for(ContO c : aconto)
		{
			c.m = medium;
			c.t = trackers;
			c.sav = new double[20];
			for(int j = 0; j < c.npl; j++)
			{
				c.p[j].m = medium;
				c.p[j].t = trackers;
			}
		}
		
		for(ContO c : aconto1)
		{
			c.m = medium;
			c.t = trackers;
			c.sav = new double[20];
			for(Plane p : c.p)
			{
				p.m = medium;
				p.t = trackers;
			}
			
			if(c.checkpoint != 0)
			{
				checkx[c.checkpoint - 1] = c.x;
				checkz[c.checkpoint - 1] = c.z;
			}
		}
		
		dis.close();
		System.gc();
	}
	
	private void regy(int i, float f, boolean flag, ContO conto, int n)
	{
		if (f > 100F)
		{
			f -= 100F;
			byte byte0 = 0;
			byte byte1 = 0;
			int j = conto.zy;
			int k = conto.xy;
			for (; j < 360; j += 360)
			{
			}
			for (; j > 360; j -= 360)
			{
			}
			if (j < 210 && j > 150)
			{
				byte0 = -1;
			}
			if (j > 330 || j < 30)
			{
				byte0 = 1;
			}
			for (; k < 360; k += 360)
			{
			}
			for (; k > 360; k -= 360)
			{
			}
			if (k < 210 && k > 150)
			{
				byte1 = -1;
			}
			if (k > 330 || k < 30)
			{
				byte1 = 1;
			}
			if (byte1 * byte0 == 0 || flag)
			{
				for (int l = 0; l < conto.npl; l++)
				{
					float f1 = 0.0F;
					for (int k1 = 0; k1 < conto.p[l].n; k1++)
					{
						if (conto.p[l].wz == 0 && py(conto.keyx[i], conto.p[l].ox[k1], conto.keyz[i], conto.p[l].oz[k1]) < clrad[n])
						{
							f1 = f / 20F * medium.random();
							conto.p[l].oz[k1] += f1 * (float)Math.sin(0.0174532925199432957 * j);
							conto.p[l].ox[k1] -= f1 * (float)Math.sin(0.0174532925199432957 * k);
						}
					}
					if (f1 == 0.0F)
					{
						continue;
					}
					if (Math.abs(f1) >= 1.0F)
					{
						conto.p[l].chip = 1;
						conto.p[l].ctmag = f1;
					}
					if (!conto.p[l].nocol && conto.p[l].glass != 1)
					{
						if (conto.p[l].bfase > 20 && conto.p[l].hsb[1] > 0.20000000000000001D)
						{
							conto.p[l].hsb[1] = 0.2F;
						}
						if (conto.p[l].bfase > 30)
						{
							if (conto.p[l].hsb[2] < 0.5D)
							{
								conto.p[l].hsb[2] = 0.5F;
							}
							if (conto.p[l].hsb[1] > 0.10000000000000001D)
							{
								conto.p[l].hsb[1] = 0.1F;
							}
						}
						if (conto.p[l].bfase > 40)
						{
							conto.p[l].hsb[1] = 0.05F;
						}
						if (conto.p[l].bfase > 50)
						{
							if (conto.p[l].hsb[2] > 0.80000000000000004D)
							{
								conto.p[l].hsb[2] = 0.8F;
							}
							conto.p[l].hsb[0] = 0.075F;
							conto.p[l].hsb[1] = 0.05F;
						}
						if (conto.p[l].bfase > 60)
						{
							conto.p[l].hsb[0] = 0.05F;
						}
						conto.p[l].bfase += f1;
						new Color(conto.p[l].c[0], conto.p[l].c[1], conto.p[l].c[2]);
						Color color = Color.getHSBColor(conto.p[l].hsb[0], conto.p[l].hsb[1], conto.p[l].hsb[2]);
						conto.p[l].c[0] = color.getRed();
						conto.p[l].c[1] = color.getGreen();
						conto.p[l].c[2] = color.getBlue();
					}
					if (conto.p[l].glass == 1)
					{
						conto.p[l].gr += Math.abs(f1 * 1.5D);
					}
				}
			}
			if (byte1 * byte0 == -1)
			{
				int i1 = 0;
				int j1 = 1;
				for (int l1 = 0; l1 < conto.npl; l1++)
				{
					float f2 = 0.0F;
					for (int i2 = 0; i2 < conto.p[l1].n; i2++)
					{
						if (conto.p[l1].wz != 0)
						{
							continue;
						}
						f2 = f / 15F * medium.random();
						if ((Math.abs(conto.p[l1].oy[i2] - flipy[n] - squash[n]) < msquash[n] * 3 || conto.p[l1].oy[i2] < flipy[n] + squash[n]) && squash[n] < msquash[n])
						{
							conto.p[l1].oy[i2] += f2;
							i1 = (int) (i1 + f2);
							j1++;
						}
					}
					if (conto.p[l1].glass == 1)
					{
						conto.p[l1].gr += 5;
					} else if (f2 != 0.0F)
					{
						conto.p[l1].bfase += f2;
					}
					if (Math.abs(f2) >= 1.0F)
					{
						conto.p[l1].chip = 1;
						conto.p[l1].ctmag = f2;
					}
				}
				squash[n] += i1 / j1;
			}
		}
	}

	private void regx(int i, float f, ContO conto, int n)
	{
		if (Math.abs(f) > 100F)
		{
			if (f > 100F)
			{
				f -= 100F;
			}
			if (f < -100F)
			{
				f += 100F;
			}
			for (int j = 0; j < conto.npl; j++)
			{
				float f1 = 0.0F;
				for (int k = 0; k < conto.p[j].n; k++)
				{
					if (conto.p[j].wz == 0 && py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i], conto.p[j].oz[k]) < clrad[n])
					{
						f1 = f / 20F * medium.random();
						conto.p[j].oz[k] -= f1 * (float)Math.sin(0.0174532925199432957 * conto.xz) * (float)Math.cos(0.0174532925199432957 * conto.zy);
						conto.p[j].ox[k] += f1 * (float)Math.cos(0.0174532925199432957 * conto.xz) * (float)Math.cos(0.0174532925199432957 * conto.xy);
					}
				}
				if (f1 == 0.0F)
				{
					continue;
				}
				if (Math.abs(f1) >= 1.0F)
				{
					conto.p[j].chip = 1;
					conto.p[j].ctmag = f1;
				}
				if (!conto.p[j].nocol && conto.p[j].glass != 1)
				{
					if (conto.p[j].bfase > 20 && conto.p[j].hsb[1] > 0.20000000000000001D)
					{
						conto.p[j].hsb[1] = 0.2F;
					}
					if (conto.p[j].bfase > 30)
					{
						if (conto.p[j].hsb[2] < 0.5D)
						{
							conto.p[j].hsb[2] = 0.5F;
						}
						if (conto.p[j].hsb[1] > 0.10000000000000001D)
						{
							conto.p[j].hsb[1] = 0.1F;
						}
					}
					if (conto.p[j].bfase > 40)
					{
						conto.p[j].hsb[1] = 0.05F;
					}
					if (conto.p[j].bfase > 50)
					{
						if (conto.p[j].hsb[2] > 0.80000000000000004D)
						{
							conto.p[j].hsb[2] = 0.8F;
						}
						conto.p[j].hsb[0] = 0.075F;
						conto.p[j].hsb[1] = 0.05F;
					}
					if (conto.p[j].bfase > 60)
					{
						conto.p[j].hsb[0] = 0.05F;
					}
					conto.p[j].bfase += Math.abs(f1);
					new Color(conto.p[j].c[0], conto.p[j].c[1], conto.p[j].c[2]);
					Color color = Color.getHSBColor(conto.p[j].hsb[0], conto.p[j].hsb[1], conto.p[j].hsb[2]);
					conto.p[j].c[0] = color.getRed();
					conto.p[j].c[1] = color.getGreen();
					conto.p[j].c[2] = color.getBlue();
				}
				if (conto.p[j].glass == 1)
				{
					conto.p[j].gr += Math.abs(f1 * 1.5D);
				}
			}
		}
	}

	private void regz(int i, float f, ContO conto, int n)
	{
		if (Math.abs(f) > 100F)
		{
			if (f > 100F)
			{
				f -= 100F;
			}
			if (f < -100F)
			{
				f += 100F;
			}
			for (int j = 0; j < conto.npl; j++)
			{
				float f1 = 0.0F;
				for (int k = 0; k < conto.p[j].n; k++)
				{
					if (conto.p[j].wz == 0 && py(conto.keyx[i], conto.p[j].ox[k], conto.keyz[i], conto.p[j].oz[k]) < clrad[n])
					{
						f1 = f / 20F * medium.random();
						conto.p[j].oz[k] += f1 * (float)Math.cos(0.0174532925199432957 * conto.xz) * (float)Math.cos(0.0174532925199432957 * conto.zy);
						conto.p[j].ox[k] += f1 * (float)Math.sin(0.0174532925199432957 * conto.xz) * (float)Math.cos(0.0174532925199432957 * conto.xy);
					}
				}
				if (f1 == 0.0F)
				{
					continue;
				}
				if (Math.abs(f1) >= 1.0F)
				{
					conto.p[j].chip = 1;
					conto.p[j].ctmag = f1;
				}
				if (!conto.p[j].nocol && conto.p[j].glass != 1)
				{
					if (conto.p[j].bfase > 20 && conto.p[j].hsb[1] > 0.20000000000000001D)
					{
						conto.p[j].hsb[1] = 0.2F;
					}
					if (conto.p[j].bfase > 30)
					{
						if (conto.p[j].hsb[2] < 0.5D)
						{
							conto.p[j].hsb[2] = 0.5F;
						}
						if (conto.p[j].hsb[1] > 0.10000000000000001D)
						{
							conto.p[j].hsb[1] = 0.1F;
						}
					}
					if (conto.p[j].bfase > 40)
					{
						conto.p[j].hsb[1] = 0.05F;
					}
					if (conto.p[j].bfase > 50)
					{
						if (conto.p[j].hsb[2] > 0.80000000000000004D)
						{
							conto.p[j].hsb[2] = 0.8F;
						}
						conto.p[j].hsb[0] = 0.075F;
						conto.p[j].hsb[1] = 0.05F;
					}
					if (conto.p[j].bfase > 60)
					{
						conto.p[j].hsb[0] = 0.05F;
					}
					conto.p[j].bfase += Math.abs(f1);
					new Color(conto.p[j].c[0], conto.p[j].c[1], conto.p[j].c[2]);
					Color color = Color.getHSBColor(conto.p[j].hsb[0], conto.p[j].hsb[1], conto.p[j].hsb[2]);
					conto.p[j].c[0] = color.getRed();
					conto.p[j].c[1] = color.getGreen();
					conto.p[j].c[2] = color.getBlue();
				}
				if (conto.p[j].glass == 1)
				{
					conto.p[j].gr += Math.abs(f1 * 1.5D);
				}
			}
		}
	}
	
	private int py(int i, int j, int k, int l)
	{
		return (i - j) * (i - j) + (k - l) * (k - l);
	}
	
	private void colorCar(ContO conto, int i)
	{
		if (!plnames[i].contains("MadBot"))
		{
			for (int j = 0; j < conto.npl; j++)
			{
				if (conto.p[j].colnum == 1)
				{
					Color color = Color.getHSBColor(allrnp[i][0], allrnp[i][1], 1.0F - allrnp[i][2]);
					conto.p[j].oc[0] = color.getRed();
					conto.p[j].oc[1] = color.getGreen();
					conto.p[j].oc[2] = color.getBlue();
				}
				if (conto.p[j].colnum == 2)
				{
					Color color1 = Color.getHSBColor(allrnp[i][3], allrnp[i][4], 1.0F - allrnp[i][5]);
					conto.p[j].oc[0] = color1.getRed();
					conto.p[j].oc[1] = color1.getGreen();
					conto.p[j].oc[2] = color1.getBlue();
				}
			}
		} else
		{
			for (int k = 0; k < conto.npl; k++)
			{
				if (conto.p[k].colnum == 1)
				{
					conto.p[k].oc[0] = conto.fcol[0];
					conto.p[k].oc[1] = conto.fcol[1];
					conto.p[k].oc[2] = conto.fcol[2];
				}
				if (conto.p[k].colnum == 2)
				{
					conto.p[k].oc[0] = conto.scol[0];
					conto.p[k].oc[1] = conto.scol[1];
					conto.p[k].oc[2] = conto.scol[2];
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			File[] files = new File(new File("").getAbsolutePath()).listFiles((directory, fileName) -> fileName.endsWith(".trk"));
			
			Arrays.sort(files, (f1, f2) -> Long.valueOf(f2.lastModified()).compareTo(f1.lastModified()));
			
			String[] list = new String[files.length];
			
			for(int i = 0; i < files.length; i++)
			{
				String temp = files[i].getName();
				list[i] = temp.substring(0, temp.length() - 4);
			}
			
			JFrame fr = new JFrame("Select Replay File");
			JComboBox<String> filelist = new JComboBox<>(list);
			JLabel wlbl = new JLabel("Width  :  ");
			JTextField wdth = new JTextField("800");
			JLabel hlbl = new JLabel("Height  :  ");
			JTextField hght = new JTextField("450");
			JLabel flbl = new JLabel("FPS  :  ");
			JTextField fpstxt = new JTextField("30");
			JCheckBox full = new JCheckBox("GUI Layover");
			JCheckBox border = new JCheckBox("No Frame Border");
			JCheckBox dist = new JCheckBox("Show Distance [Racing]");
			JCheckBox time = new JCheckBox("Show Relative Time [Racing]");
			JCheckBox track = new JCheckBox("End Timing on 1st [Racing]");
			
			JRadioButton race = new JRadioButton("Race");
			JRadioButton waste = new JRadioButton("Waste");
			JRadioButton both = new JRadioButton("Both");
			JButton start = new JButton("OK");
			both.setSelected(true);
			
			ButtonGroup jb = new ButtonGroup();
			jb.add(race);
			jb.add(waste);
			jb.add(both);
			
			SpringLayout layout = new SpringLayout();
			
			Container contain = fr.getContentPane();
			layout.putConstraint(SpringLayout.WEST, filelist, 10, SpringLayout.WEST, contain);
			layout.putConstraint(SpringLayout.NORTH, filelist, 10, SpringLayout.NORTH, contain);
			layout.putConstraint(SpringLayout.EAST, filelist, -10, SpringLayout.EAST, contain);
			
			// Set Vertical Pos of TextBox
			layout.putConstraint(SpringLayout.NORTH, wdth, 10, SpringLayout.SOUTH, filelist);
			layout.putConstraint(SpringLayout.NORTH, hght, 10, SpringLayout.SOUTH, wdth);
			layout.putConstraint(SpringLayout.NORTH, fpstxt, 10, SpringLayout.SOUTH, hght);
			
			// Set Vertical Pos of Label
			layout.putConstraint(SpringLayout.NORTH, wlbl, 2, SpringLayout.NORTH, wdth);
			layout.putConstraint(SpringLayout.NORTH, hlbl, 2, SpringLayout.NORTH, hght);
			layout.putConstraint(SpringLayout.NORTH, flbl, 2, SpringLayout.NORTH, fpstxt);
			
			// Set Horizontal Pos of Label
			layout.putConstraint(SpringLayout.EAST, wlbl, 0, SpringLayout.EAST, hlbl);
			layout.putConstraint(SpringLayout.WEST, hlbl, 0, SpringLayout.WEST, filelist);
			layout.putConstraint(SpringLayout.EAST, flbl, 0, SpringLayout.EAST, hlbl);
			
			// Set Horizontal Pos of TextBox
			layout.putConstraint(SpringLayout.EAST, wdth, -10, SpringLayout.EAST, contain);
			layout.putConstraint(SpringLayout.EAST, hght, -10, SpringLayout.EAST, contain);
			layout.putConstraint(SpringLayout.EAST, fpstxt, -10, SpringLayout.EAST, contain);
			
			layout.putConstraint(SpringLayout.WEST, wdth, 0, SpringLayout.EAST, hlbl);
			layout.putConstraint(SpringLayout.WEST, hght, 0, SpringLayout.EAST, hlbl);
			layout.putConstraint(SpringLayout.WEST, fpstxt, 0, SpringLayout.EAST, hlbl);
			
			// Set Vertical & Horizontal Position of CheckBoxes
			
			layout.putConstraint(SpringLayout.WEST, full, 0, SpringLayout.WEST, filelist);
			layout.putConstraint(SpringLayout.WEST, border, 0, SpringLayout.WEST, filelist);
			layout.putConstraint(SpringLayout.WEST, dist, 0, SpringLayout.WEST, filelist);
			layout.putConstraint(SpringLayout.WEST, time, 0, SpringLayout.WEST, filelist);
			layout.putConstraint(SpringLayout.WEST, track, 0, SpringLayout.WEST, filelist);
			
			layout.putConstraint(SpringLayout.SOUTH, full, 0, SpringLayout.NORTH, border);
			layout.putConstraint(SpringLayout.SOUTH, border, 0, SpringLayout.NORTH, dist);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, dist, 0, SpringLayout.VERTICAL_CENTER, start);
			layout.putConstraint(SpringLayout.NORTH, time, 0, SpringLayout.SOUTH, dist);
			layout.putConstraint(SpringLayout.NORTH, track, 0, SpringLayout.SOUTH, time);
			
			// Set Vertical & Horizontal Positions of Radio Buttons
			
			layout.putConstraint(SpringLayout.WEST, race, 0, SpringLayout.WEST, waste);
			layout.putConstraint(SpringLayout.WEST, waste, 20, SpringLayout.EAST, time);
			layout.putConstraint(SpringLayout.WEST, both, 0, SpringLayout.WEST, waste);
			
			layout.putConstraint(SpringLayout.SOUTH, race, 0, SpringLayout.NORTH, waste);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, waste, 0, SpringLayout.VERTICAL_CENTER, start);
			layout.putConstraint(SpringLayout.NORTH, both, 0, SpringLayout.SOUTH, waste);
			
			// Set Position of Button
			
			layout.putConstraint(SpringLayout.NORTH, start, 10, SpringLayout.SOUTH, fpstxt);
			layout.putConstraint(SpringLayout.SOUTH, start, -10, SpringLayout.SOUTH, contain);
			layout.putConstraint(SpringLayout.EAST, start, -10, SpringLayout.EAST, contain);
			layout.putConstraint(SpringLayout.WEST, start, 20, SpringLayout.EAST, waste);

			fr.setLayout(layout);
			fr.add(filelist);
			fr.add(wlbl);
			fr.add(wdth);
			fr.add(hlbl);
			fr.add(hght);
			fr.add(flbl);
			fr.add(fpstxt);
			fr.add(full);
			fr.add(border);
			fr.add(dist);
			fr.add(time);
			fr.add(track);
			fr.add(race);
			fr.add(waste);
			fr.add(both);
			fr.add(start);
			fr.setLocationRelativeTo(null);
			fr.setSize(new Dimension(550, 300));
			fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fr.setVisible(true);
			
			start.addActionListener(arg0 -> {
                try
                {
                    final Frame frame1 = new Frame("NFM Replay Viewer");
                    ReplayViewer rply = new ReplayViewer(new File("").getAbsolutePath() + File.separator + filelist.getSelectedItem().toString() + ".trk", Integer.parseInt(wdth.getText()), Integer.parseInt(hght.getText()));
                    rply.fps = Float.parseFloat(fpstxt.getText());

                    if (both.isSelected())
                        rply.gametype = 0;
                    else if (race.isSelected())
                        rply.gametype = 1;
                    else if (waste.isSelected())
                        rply.gametype = 2;

                    if(!full.isSelected())
                    {
                        if(rply.multi)
                            rply.setPreferredSize(new Dimension(rply.medium.w, rply.medium.h + 127));
                        else
                            rply.setPreferredSize(new Dimension(rply.medium.w, rply.medium.h + 100));
                    } else
                    {
                        rply.setPreferredSize(new Dimension(rply.medium.w, rply.medium.h));
                        rply.layover = true;
                    }

                    rply.dist = dist.isSelected();
                    rply.time = time.isSelected();
                    rply.endtype = track.isSelected();

                    float[] r = new float[3];

                    if(rply.layover)
                        Color.RGBtoHSB(rply.medium.csky[0], rply.medium.csky[1], rply.medium.csky[2], r);
                    else
                        Color.RGBtoHSB(rply.medium.cgrnd[0], rply.medium.cgrnd[1], rply.medium.cgrnd[2], r);

                    rply.base = new Color(Color.HSBtoRGB(r[0], r[1], r[2]));

                    rply.hue = r[0];
                    rply.saturation = new Slider(1, r[1]);
                    rply.bright = new Slider(1, r[2]);

                    rply.drawDist = new Slider(10, 1);
                    drawGround = new Slider(120, 12);

                    frame1.setUndecorated(border.isSelected());
                    frame1.add(rply);

                    frame1.setResizable(false);
                    frame1.pack();
                    frame1.setLocationRelativeTo(null);
                    frame1.setVisible(true);
                    fr.dispose();

                    frame1.addWindowListener(new WindowAdapter()
                    {
                        public void windowClosing(WindowEvent we)
                        {
                            rply.running = false;
                            frame1.setVisible(false);
                            frame1.dispose();
                            System.exit(0);
                        }
                    });

                    frame1.addKeyListener(rply.kadapter);
                    frame1.addMouseListener(rply.madapter);
                    frame1.addMouseMotionListener(rply.madapter);
                    rply.addKeyListener(rply.kadapter);
                    rply.addMouseListener(rply.madapter);
                    rply.addMouseMotionListener(rply.madapter);

                    rply.start();
                } catch (Exception e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Viewer cannot be opened!", "Error", 0);
                }
            });
		} catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Viewer cannot be opened!", "Error", 0);
		}
	}
}
