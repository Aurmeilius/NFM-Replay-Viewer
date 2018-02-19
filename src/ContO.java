// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name: ContO.java
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.Arrays;

class ContO implements Serializable
{
	private static final long serialVersionUID = 1L;
	ContO(ContO conto, int i, int j, int k)
	{
		npl = 0;
		x = 0;
		y = 0;
		z = 0;
		xz = 0;
		xy = 0;
		zy = 0;
		wxz = 0;
		wzy = 0;
		dist = 0;
		maxR = 0;
		disp = 0;
		disline = 14;
		shadow = false;
		noline = false;
		decor = false;
		grounded = 1.0F;
		grat = 0;
		keyx = new int[4];
		keyz = new int[4];
		sprkat = 0;
		tnt = 0;
		ust = 0;
		srx = 0;
		sry = 0;
		srz = 0;
		rcx = 0.0F;
		rcy = 0.0F;
		rcz = 0.0F;
		sprk = 0;
		elec = false;
		roted = false;
		edl = new int[4];
		edr = new int[4];
		fix = false;
		fcnt = 0;
		checkpoint = 0;
		m = conto.m;
		t = conto.t;
		npl = conto.npl;
		maxR = conto.maxR;
		disp = conto.disp;
		disline = conto.disline;
		noline = conto.noline;
		shadow = conto.shadow;
		grounded = conto.grounded;
		decor = conto.decor;
		grat = conto.grat;
		sprkat = conto.sprkat;
		p = new Plane[conto.npl];
		for (int i1 = 0; i1 < npl; i1++)
		{
			if (conto.p[i1].master == 1)
			{
				conto.p[i1].n = 20;
			}
			p[i1] = new Plane(m, t, conto.p[i1].ox, conto.p[i1].oz, conto.p[i1].oy, conto.p[i1].n, conto.p[i1].oc, conto.p[i1].glass, conto.p[i1].gr, conto.p[i1].fs, conto.p[i1].wx, conto.p[i1].wy, conto.p[i1].wz, conto.disline, conto.p[i1].bfase, conto.p[i1].road, conto.p[i1].light, conto.p[i1].solo);
		}
		x = i;
		y = j;
		z = k;
		xz = 0;
		xy = 0;
		zy = 0;
		for (int j1 = 0; j1 < npl; j1++)
		{
			p[j1].colnum = conto.p[j1].colnum;
			p[j1].master = conto.p[j1].master;
			p[j1].rot(p[j1].ox, p[j1].oz, 0, 0, 0, p[j1].n);
			p[j1].loadprojf();
		}
		if (conto.tnt != 0)
		{
			for (int k1 = 0; k1 < conto.tnt; k1++)
			{
				t.xy[t.nt] = (int) (conto.txy[k1] * (float)Math.cos(0.0174532925199432957 * 0) - conto.tzy[k1] * (float)Math.sin(0.0174532925199432957 * 0));
				t.zy[t.nt] = (int) (conto.tzy[k1] * (float)Math.cos(0.0174532925199432957 * 0) + conto.txy[k1] * (float)Math.sin(0.0174532925199432957 * 0));
				for (int k2 = 0; k2 < 3; k2++)
				{
					t.c[t.nt][k2] = (int) (conto.tc[k1][k2] + conto.tc[k1][k2] * (m.snap[k2] / 100F));
					if (t.c[t.nt][k2] > 255)
					{
						t.c[t.nt][k2] = 255;
					}
					if (t.c[t.nt][k2] < 0)
					{
						t.c[t.nt][k2] = 0;
					}
				}
				t.x[t.nt] = (int) (x + conto.tx[k1] * (float)Math.cos(0.0174532925199432957 * 0) - conto.tz[k1] * (float)Math.sin(0.0174532925199432957 * 0));
				t.z[t.nt] = (int) (z + conto.tz[k1] * (float)Math.cos(0.0174532925199432957 * 0) + conto.tx[k1] * (float)Math.sin(0.0174532925199432957 * 0));
				t.y[t.nt] = y + conto.ty[k1];
				t.skd[t.nt] = conto.skd[k1];
				t.dam[t.nt] = conto.dam[k1];
				t.notwall[t.nt] = conto.notwall[k1];
                t.decor[t.nt] = decor;
				int l2 = Math.abs(0);
				if (l2 == 180)
				{
					l2 = 0;
				}
				t.radx[t.nt] = (int) Math.abs(conto.tradx[k1] * (float)Math.cos(0.0174532925199432957 * l2) + conto.tradz[k1] * (float)Math.sin(0.0174532925199432957 * l2));
				t.radz[t.nt] = (int) Math.abs(conto.tradx[k1] * (float)Math.sin(0.0174532925199432957 * l2) + conto.tradz[k1] * (float)Math.cos(0.0174532925199432957 * l2));
				t.rady[t.nt] = conto.trady[k1];
				t.nt++;
			}
		}
		for (int l1 = 0; l1 < 4; l1++)
		{
			keyx[l1] = conto.keyx[l1];
			keyz[l1] = conto.keyz[l1];
		}
		if (shadow)
		{
			stg = new int[20];
			sx = new int[20];
			sy = new int[20];
			sz = new int[20];
			scx = new int[20];
			scz = new int[20];
			osmag = new float[20];
			sav = new double[20];
			smag = new float[20][8];
			srgb = new int[20][3];
			sbln = new float[20];
			ust = 0;
			for (int i2 = 0; i2 < 20; i2++)
			{
				stg[i2] = 0;
			}
			rtg = new int[100];
			rbef = new boolean[100];
			rx = new int[100];
			ry = new int[100];
			rz = new int[100];
			vrx = new float[100];
			vry = new float[100];
			vrz = new float[100];
			for (int j2 = 0; j2 < 100; j2++)
			{
				rtg[j2] = 0;
			}
		}
	}

	void d(Graphics2D graphics2d)
	{
		float sin_c_xz = (float)StrictMath.sin(0.0174532925199432957 * xz);
		float cos_c_xz = (float)StrictMath.cos(0.0174532925199432957 * xz);
		float sin_c_zy = (float)StrictMath.sin(0.0174532925199432957 * zy);
		float cos_c_zy = (float)StrictMath.cos(0.0174532925199432957 * zy);
		float sin_c_xy = (float)StrictMath.sin(0.0174532925199432957 * xy);
		float cos_c_xy = (float)StrictMath.cos(0.0174532925199432957 * xy);
		
		if (dist != 0)
		{
			dist = 0;
		}
		int h = m.cx + (int) ((x - m.x - m.cx) * ReplayViewer.cos_m_xz - (z - m.z - m.cz) * ReplayViewer.sin_m_xz);
		int j = m.cz + (int) ((x - m.x - m.cx) * ReplayViewer.sin_m_xz + (z - m.z - m.cz) * ReplayViewer.cos_m_xz);
		int q = m.cy + (int) ((y - m.y - m.cy) * ReplayViewer.cos_m_zy - (j - m.cz) * ReplayViewer.sin_m_zy);
		int k = m.cz + (int) ((y - m.y - m.cy) * ReplayViewer.sin_m_zy + (j - m.cz) * ReplayViewer.cos_m_zy);
		int i = m.cx + (int) ((h - m.cx) * ReplayViewer.cos_m_xy - (q - m.cy) * ReplayViewer.sin_m_xy);
		int l = xs(i + maxR, k) - xs(i - maxR, k);
		
		int q1 = xs(i + maxR * 2, k);
		int f7 = xs(i - maxR * 2, k);
		
		ReplayViewer.iseenx = ((q1 > m.iw || f7 > m.iw) && (q1 < m.w || f7 < m.w) && k > -maxR && (k < m.fade[disline] + maxR || m.trk != 0) && (!decor || m.resdown != 2 && m.trk != 1));
		
		if (shadow && ReplayViewer.iseenx)
		{
			if (!m.crs)
			{
				//if (k < 2000)
				{
					boolean flag = false;
					if (t.ncx != 0 || t.ncz != 0)
					{
						int i2 = (x - t.sx) / 3000;
						if (i2 > t.ncx)
						{
							i2 = t.ncx;
						}
						if (i2 < 0)
						{
							i2 = 0;
						}
						int i3 = (z - t.sz) / 3000;
						if (i3 > t.ncz)
						{
							i3 = t.ncz;
						}
						if (i3 < 0)
						{
							i3 = 0;
						}
						int k3 = t.sect[i2][i3].length - 1;
						do
						{
							if (k3 < 0)
							{
								break;
							}
							int i5 = t.sect[i2][i3][k3];
							if (Math.abs(t.zy[i5]) != 90 && Math.abs(t.xy[i5]) != 90 && Math.abs(x - t.x[i5]) < t.radx[i5] + maxR && Math.abs(z - t.z[i5]) < t.radz[i5] + maxR && (!t.decor[i5] || m.resdown != 2))
							{
								flag = true;
								break;
							}
							k3--;
						} while (true);
					}
					if (flag)
					{
						for (int j2 = 0; j2 < npl; j2++)
						{
							p[j2].s(graphics2d, x - m.x, y - m.y, z - m.z, sin_c_xy, cos_c_xy, sin_c_zy, cos_c_zy, sin_c_xz, cos_c_xz, 0);
						}
					} else
					{
						int q2 = m.cy + (int) ((m.ground - m.cy) * ReplayViewer.cos_m_zy - (j - m.cz) * ReplayViewer.sin_m_zy);
						int j3 = m.cz + (int) ((m.ground - m.cy) * ReplayViewer.sin_m_zy + (j - m.cz) * ReplayViewer.cos_m_zy);
						int k2 = m.cy + (int) ((h - m.cx) * ReplayViewer.sin_m_xy + (q2 - m.cy) * ReplayViewer.cos_m_xy);
						
						int s1 = ys(k2 + maxR, j3);
						int s2 = ys(k2 - maxR, j3);
						
						if ((s1 > 0 || s2 > 0) && (s1 < m.h || s2 < m.h))
						{
							for (int l3 = 0; l3 < npl; l3++)
							{
								p[l3].s(graphics2d, x - m.x, y - m.y, z - m.z, sin_c_xy, cos_c_xy, sin_c_zy, cos_c_zy, sin_c_xz, cos_c_xz, 1);
							}
						}
					}
					m.addsp(x - m.x, z - m.z, (int) (maxR * 0.80000000000000004D));
				} /*else
				{
					lowshadow(graphics2d, k);
				}*/
			} else
			{
				for (int i1 = 0; i1 < npl; i1++)
				{
					p[i1].s(graphics2d, x - m.x, y - m.y, z - m.z, sin_c_xy, cos_c_xy, sin_c_zy, cos_c_zy, sin_c_xz, cos_c_xz, 2);
				}
			}
		}
		
		int y1 = m.cy + (int) ((y - m.y - m.cy) * ReplayViewer.cos_m_zy - (j - m.cz) * ReplayViewer.sin_m_zy);
		int j1 = m.cy + (int) ((h - m.cx) * ReplayViewer.sin_m_xy + (y1 - m.cy) * ReplayViewer.cos_m_xy);
		
		int f1 = ys(j1 + maxR, k);
		int f2 = ys(j1 - maxR, k);
		
		ReplayViewer.iseeny = (f1 > m.ih || f2 > m.ih) && (f1 < m.h || f2 < m.h);
		
		if (elec && m.noelec == 0)
		{
			electrify(graphics2d);
		}
		if (fix)
		{
			fixit(graphics2d);
		}
		if (checkpoint != 0 && checkpoint - 1 == m.checkpoint && ReplayViewer.iseenx && ReplayViewer.iseeny)
		{
			l = -1;
		}
		if (shadow)
		{
			dist = Math.sqrt((double)(m.x + m.cx - x) * (double)(m.x + m.cx - x) + (double)(m.z - z) * (double)(m.z - z) + (double)(m.y + m.cy - y) * (double)(m.y + m.cy - y));
			for (int l2 = 0; l2 < 20; l2++)
			{
				if (stg[l2] != 0)
				{
					pdust(l2, graphics2d, true);
				}
			}
			dsprk(graphics2d, true);
		}
		
		sin_c_xz = (float)StrictMath.sin(0.0174532925199432957 * xz);
		cos_c_xz = (float)StrictMath.cos(0.0174532925199432957 * xz);
		sin_c_zy = (float)StrictMath.sin(0.0174532925199432957 * zy);
		cos_c_zy = (float)StrictMath.cos(0.0174532925199432957 * zy);
		sin_c_xy = (float)StrictMath.sin(0.0174532925199432957 * xy);
		cos_c_xy = (float)StrictMath.cos(0.0174532925199432957 * xy);
		
		if (ReplayViewer.iseenx && ReplayViewer.iseeny)
		{
			double[][] ai = new double[npl][2];
			
			for (int i4 = 0; i4 < npl; i4++)
			{
				ai[i4][0] = i4;
				ai[i4][1] = p[i4].av;
			}
			
			Arrays.sort(ai, (arg0, arg1) -> Double.compare(arg1[1], arg0[1]));
			
			for (int k4 = 0; k4 < npl; k4++)
			{
				if(p[(int)ai[k4][0]].glass == 1)
					graphics2d.setComposite(AlphaComposite.getInstance(3, .5F));
				
				p[(int)ai[k4][0]].d(graphics2d, x - m.x, y - m.y, z - m.z, xz, xy, zy, sin_c_xy, cos_c_xy, sin_c_zy, cos_c_zy, sin_c_xz, cos_c_xz, wxz, wzy, noline, l);
				
				if(p[(int)ai[k4][0]].glass == 1)
					graphics2d.setComposite(AlphaComposite.getInstance(3, 1F));
			}
		}
		
		if (shadow)
		{
			for (int l4 = 0; l4 < 20; l4++)
			{
				if (stg[l4] != 0)
				{
					pdust(l4, graphics2d, false);
				}
			}
			dsprk(graphics2d, false);
		}
	}

	private void fixit(Graphics2D graphics2d)
	{
		if (fcnt == 1)
		{
			for (int i = 0; i < npl; i++)
			{
				p[i].hsb[0] = 0.57F;
				p[i].hsb[2] = 0.8F;
				p[i].hsb[1] = 0.8F;
				Color color = Color.getHSBColor(p[i].hsb[0], p[i].hsb[1], p[i].hsb[2]);
				int l = (int) (color.getRed() + color.getRed() * (m.snap[0] / 100F));
				if (l > 255)
				{
					l = 255;
				}
				if (l < 0)
				{
					l = 0;
				}
				int i1 = (int) (color.getGreen() + color.getGreen() * (m.snap[1] / 100F));
				if (i1 > 255)
				{
					i1 = 255;
				}
				if (i1 < 0)
				{
					i1 = 0;
				}
				int l1 = (int) (color.getBlue() + color.getBlue() * (m.snap[2] / 100F));
				if (l1 > 255)
				{
					l1 = 255;
				}
				if (l1 < 0)
				{
					l1 = 0;
				}
				Color.RGBtoHSB(l, i1, l1, p[i].hsb);
				p[i].flx = 1;
			}
		}
		if (fcnt == 2)
		{
			for (int j = 0; j < npl; j++)
			{
				p[j].flx = 1;
			}
		}
		if (fcnt == 4)
		{
			for (int k = 0; k < npl; k++)
			{
				p[k].flx = 3;
			}
		}
		if ((fcnt == 1 || fcnt > 2) && fcnt != 9 && ReplayViewer.iseenx && ReplayViewer.iseeny)
		{
			int ai[] = new int[8];
			int ai1[] = new int[8];
			int ai2[] = new int[4];
			for (int j1 = 0; j1 < 4; j1++)
			{
				ai[j1] = keyx[j1] + x - m.x;
				ai1[j1] = grat + y - m.y;
				ai2[j1] = keyz[j1] + z - m.z;
			}
			rot(ai, ai1, x - m.x, y - m.y, xy, 4);
			rot(ai1, ai2, y - m.y, z - m.y, zy, 4);
			rot(ai, ai2, x - m.x, z - m.z, xz, 4);
			rot(ai, ai2, m.cx, m.cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, 4);
			rot(ai1, ai2, m.cy, m.cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, 4);
			rot(ai, ai1, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 4);
			int k1 = 0;
			int i2 = 0;
			int j2 = 0;
			for (int k2 = 0; k2 < 4; k2++)
			{
				for (int i3 = 0; i3 < 4; i3++)
				{
					if (Math.abs(ai[k2] - ai[i3]) > k1)
					{
						k1 = Math.abs(ai[k2] - ai[i3]);
					}
					if (Math.abs(ai1[k2] - ai1[i3]) > i2)
					{
						i2 = Math.abs(ai1[k2] - ai1[i3]);
					}
					if (py(ai[k2], ai[i3], ai1[k2], ai1[i3]) > j2)
					{
						j2 = py(ai[k2], ai[i3], ai1[k2], ai1[i3]);
					}
				}
			}
			j2 = (int) (Math.sqrt(j2) / 1.5D);
			if (k1 < j2)
			{
				k1 = j2;
			}
			if (i2 < j2)
			{
				i2 = j2;
			}
			
			int l2 = m.cx + (int) ((x - m.x - m.cx) * ReplayViewer.cos_m_xz - (z - m.z - m.cz) * ReplayViewer.sin_m_xz);
			int j3 = m.cz + (int) ((x - m.x - m.cx) * ReplayViewer.sin_m_xz + (z - m.z - m.cz) * ReplayViewer.cos_m_xz);
			int k3 = m.cy + (int) ((y - m.y - m.cy) * ReplayViewer.cos_m_zy - (j3 - m.cz) * ReplayViewer.sin_m_zy);
			j3 = m.cz + (int) ((y - m.y - m.cy) * ReplayViewer.sin_m_zy + (j3 - m.cz) * ReplayViewer.cos_m_zy);
			
			ai[0] = xs((int) (l2 - k1 / 0.80000000000000004D - m.random() * (k1 / 2.3999999999999999D)), j3);
			ai1[0] = ys((int) (k3 - i2 / 1.9199999999999999D - m.random() * (i2 / 5.6699999999999999D)), j3);
			ai[1] = xs((int) (l2 - k1 / 0.80000000000000004D - m.random() * (k1 / 2.3999999999999999D)), j3);
			ai1[1] = ys((int) (k3 + i2 / 1.9199999999999999D + m.random() * (i2 / 5.6699999999999999D)), j3);
			ai[2] = xs((int) (l2 - k1 / 1.9199999999999999D - m.random() * (k1 / 5.6699999999999999D)), j3);
			ai1[2] = ys((int) (k3 + i2 / 0.80000000000000004D + m.random() * (i2 / 2.3999999999999999D)), j3);
			ai[3] = xs((int) (l2 + k1 / 1.9199999999999999D + m.random() * (k1 / 5.6699999999999999D)), j3);
			ai1[3] = ys((int) (k3 + i2 / 0.80000000000000004D + m.random() * (i2 / 2.3999999999999999D)), j3);
			ai[4] = xs((int) (l2 + k1 / 0.80000000000000004D + m.random() * (k1 / 2.3999999999999999D)), j3);
			ai1[4] = ys((int) (k3 + i2 / 1.9199999999999999D + m.random() * (i2 / 5.6699999999999999D)), j3);
			ai[5] = xs((int) (l2 + k1 / 0.80000000000000004D + m.random() * (k1 / 2.3999999999999999D)), j3);
			ai1[5] = ys((int) (k3 - i2 / 1.9199999999999999D - m.random() * (i2 / 5.6699999999999999D)), j3);
			ai[6] = xs((int) (l2 + k1 / 1.9199999999999999D + m.random() * (k1 / 5.6699999999999999D)), j3);
			ai1[6] = ys((int) (k3 - i2 / 0.80000000000000004D - m.random() * (i2 / 2.3999999999999999D)), j3);
			ai[7] = xs((int) (l2 - k1 / 1.9199999999999999D - m.random() * (k1 / 5.6699999999999999D)), j3);
			ai1[7] = ys((int) (k3 - i2 / 0.80000000000000004D - m.random() * (i2 / 2.3999999999999999D)), j3);
			if (fcnt == 3)
			{
				rot(ai, ai1, xs(l2, j3), ys(k3, j3), 22, 8);
			}
			if (fcnt == 4)
			{
				rot(ai, ai1, xs(l2, j3), ys(k3, j3), 22, 8);
			}
			if (fcnt == 5)
			{
				rot(ai, ai1, xs(l2, j3), ys(k3, j3), 0, 8);
			}
			if (fcnt == 6)
			{
				rot(ai, ai1, xs(l2, j3), ys(k3, j3), -22, 8);
			}
			if (fcnt == 7)
			{
				rot(ai, ai1, xs(l2, j3), ys(k3, j3), -22, 8);
			}
			int l3 = (int) (191F + 191F * (m.snap[0] / 350F));
			if (l3 > 255)
			{
				l3 = 255;
			}
			if (l3 < 0)
			{
				l3 = 0;
			}
			int i4 = (int) (232F + 232F * (m.snap[1] / 350F));
			if (i4 > 255)
			{
				i4 = 255;
			}
			if (i4 < 0)
			{
				i4 = 0;
			}
			int j4 = (int) (255F + 255F * (m.snap[2] / 350F));
			if (j4 > 255)
			{
				j4 = 255;
			}
			if (j4 < 0)
			{
				j4 = 0;
			}
			rot(ai, ai1, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 8);
			graphics2d.setColor(new Color(l3, i4, j4));
			graphics2d.fillPolygon(ai, ai1, 8);
			ai[0] = xs((int) (l2 - k1 - m.random() * (k1 / 4)), j3);
			ai1[0] = ys((int) (k3 - i2 / 2.3999999999999999D - m.random() * (i2 / 9.5999999999999996D)), j3);
			ai[1] = xs((int) (l2 - k1 - m.random() * (k1 / 4)), j3);
			ai1[1] = ys((int) (k3 + i2 / 2.3999999999999999D + m.random() * (i2 / 9.5999999999999996D)), j3);
			ai[2] = xs((int) (l2 - k1 / 2.3999999999999999D - m.random() * (k1 / 9.5999999999999996D)), j3);
			ai1[2] = ys((int) (k3 + i2 + m.random() * (i2 / 4)), j3);
			ai[3] = xs((int) (l2 + k1 / 2.3999999999999999D + m.random() * (k1 / 9.5999999999999996D)), j3);
			ai1[3] = ys((int) (k3 + i2 + m.random() * (i2 / 4)), j3);
			ai[4] = xs((int) (l2 + k1 + m.random() * (k1 / 4)), j3);
			ai1[4] = ys((int) (k3 + i2 / 2.3999999999999999D + m.random() * (i2 / 9.5999999999999996D)), j3);
			ai[5] = xs((int) (l2 + k1 + m.random() * (k1 / 4)), j3);
			ai1[5] = ys((int) (k3 - i2 / 2.3999999999999999D - m.random() * (i2 / 9.5999999999999996D)), j3);
			ai[6] = xs((int) (l2 + k1 / 2.3999999999999999D + m.random() * (k1 / 9.5999999999999996D)), j3);
			ai1[6] = ys((int) (k3 - i2 - m.random() * (i2 / 4)), j3);
			ai[7] = xs((int) (l2 - k1 / 2.3999999999999999D - m.random() * (k1 / 9.5999999999999996D)), j3);
			ai1[7] = ys((int) (k3 - i2 - m.random() * (i2 / 4)), j3);
			l3 = (int) (213F + 213F * (m.snap[0] / 350F));
			if (l3 > 255)
			{
				l3 = 255;
			}
			if (l3 < 0)
			{
				l3 = 0;
			}
			i4 = (int) (239F + 239F * (m.snap[1] / 350F));
			if (i4 > 255)
			{
				i4 = 255;
			}
			if (i4 < 0)
			{
				i4 = 0;
			}
			j4 = (int) (255F + 255F * (m.snap[2] / 350F));
			if (j4 > 255)
			{
				j4 = 255;
			}
			if (j4 < 0)
			{
				j4 = 0;
			}
			rot(ai, ai1, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 8);
			graphics2d.setColor(new Color(l3, i4, j4));
			graphics2d.fillPolygon(ai, ai1, 8);
		}
		if (ReplayViewer.going)
		{
			if (fcnt > 7)
			{
				fcnt = 0;
				fix = false;
			} else
			{
				fcnt++;
			}
		}
	}

	private void electrify(Graphics2D graphics2d)
	{
		for (int i = 0; i < 4; i++)
		{
			if (elc[i] == 0)
			{
				edl[i] = (int) (380F - m.random() * 760F);
				edr[i] = (int) (380F - m.random() * 760F);
				elc[i] = 1;
			}
			
			if (ReplayViewer.iseenx && ReplayViewer.iseeny)
			{
				int j = (int) (edl[i] + (190F - m.random() * 380F));
				int k = (int) (edr[i] + (190F - m.random() * 380F));
				int l = (int) (m.random() * 126F);
				int i1 = (int) (m.random() * 126F);
				int ai[] = new int[8];
				int ai1[] = new int[8];
				int ai2[] = new int[8];
				for (int j1 = 0; j1 < 8; j1++)
				{
					ai2[j1] = z - m.z;
				}
				ai[0] = x - m.x - 504;
				ai1[0] = y - m.y - edl[i] - 5 - (int) (m.random() * 5F);
				ai[1] = x - m.x - 252 + i1;
				ai1[1] = y - m.y - j - 5 - (int) (m.random() * 5F);
				ai[2] = x - m.x + 252 - l;
				ai1[2] = y - m.y - k - 5 - (int) (m.random() * 5F);
				ai[3] = x - m.x + 504;
				ai1[3] = y - m.y - edr[i] - 5 - (int) (m.random() * 5F);
				ai[4] = x - m.x + 504;
				ai1[4] = y - m.y - edr[i] + 5 + (int) (m.random() * 5F);
				ai[5] = x - m.x + 252 - l;
				ai1[5] = y - m.y - k + 5 + (int) (m.random() * 5F);
				ai[6] = x - m.x - 252 + i1;
				ai1[6] = y - m.y - j + 5 + (int) (m.random() * 5F);
				ai[7] = x - m.x - 504;
				ai1[7] = y - m.y - edl[i] + 5 + (int) (m.random() * 5F);
				if (roted)
				{
					rot(ai, ai2, x - m.x, z - m.z, 90, 8);
				}
				rot(ai, ai2, m.cx, m.cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, 8);
				rot(ai1, ai2, m.cy, m.cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, 8);
				rot(ai, ai1, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 8);
				boolean flag = true;
				int k1 = 0;
				int l1 = 0;
				int i2 = 0;
				int j2 = 0;
				int ai3[] = new int[8];
				int ai4[] = new int[8];
				for (int k2 = 0; k2 < 8; k2++)
				{
					ai3[k2] = xs(ai[k2], ai2[k2]);
					ai4[k2] = ys(ai1[k2], ai2[k2]);
					if (ai4[k2] < m.ih || ai2[k2] < 10)
					{
						k1++;
					}
					if (ai4[k2] > m.h || ai2[k2] < 10)
					{
						l1++;
					}
					if (ai3[k2] < m.iw || ai2[k2] < 10)
					{
						i2++;
					}
					if (ai3[k2] > m.w || ai2[k2] < 10)
					{
						j2++;
					}
				}
				if (i2 == 8 || k1 == 8 || l1 == 8 || j2 == 8)
				{
					flag = false;
				}
				if (flag)
				{
					int l2 = (int) (160F + 160F * (m.snap[0] / 500F));
					if (l2 > 255)
					{
						l2 = 255;
					}
					if (l2 < 0)
					{
						l2 = 0;
					}
					int j3 = (int) (238F + 238F * (m.snap[1] / 500F));
					if (j3 > 255)
					{
						j3 = 255;
					}
					if (j3 < 0)
					{
						j3 = 0;
					}
					int l3 = (int) (255F + 255F * (m.snap[2] / 500F));
					if (l3 > 255)
					{
						l3 = 255;
					}
					if (l3 < 0)
					{
						l3 = 0;
					}
					l2 = (l2 * 2 + 214 * (elc[i] - 1)) / (elc[i] + 1);
					j3 = (j3 * 2 + 236 * (elc[i] - 1)) / (elc[i] + 1);
					if (m.trk == 1)
					{
						l2 = 255;
						j3 = 128;
						l3 = 0;
					}
					graphics2d.setColor(new Color(l2, j3, l3));
					graphics2d.fillPolygon(ai3, ai4, 8);
					if (ai2[0] < 4000)
					{
						int i3 = (int) (150F + 150F * (m.snap[0] / 500F));
						if (i3 > 255)
						{
							i3 = 255;
						}
						if (i3 < 0)
						{
							i3 = 0;
						}
						int k3 = (int) (227F + 227F * (m.snap[1] / 500F));
						if (k3 > 255)
						{
							k3 = 255;
						}
						if (k3 < 0)
						{
							k3 = 0;
						}
						int i4 = (int) (255F + 255F * (m.snap[2] / 500F));
						if (i4 > 255)
						{
							i4 = 255;
						}
						if (i4 < 0)
						{
							i4 = 0;
						}
						graphics2d.setColor(new Color(i3, k3, i4));
						graphics2d.drawPolygon(ai3, ai4, 8);
					}
				}
			}
			if (ReplayViewer.going)
			{
				if (elc[i] > m.random() * 60F)
				{
					elc[i] = 0;
				} else
				{
					elc[i]++;
				}
			}
		}
		if (ReplayViewer.going)
		{
			if (!roted || xz != 0)
			{
				xy += 11;
				if (xy > 360)
				{
					xy -= 360;
				}
			} else
			{
				zy += 11;
				if (zy > 360)
				{
					zy -= 360;
				}
			}
		}
	}

	private void pdust(int i, Graphics2D graphics2d, boolean flag)
	{
		if (flag)
		{
			sav[i] = Math.sqrt((double)(m.x + m.cx - sx[i]) * (double)(m.x + m.cx - sx[i]) + (double)(m.y + m.cy - sy[i]) * (double)(m.y + m.cy - sy[i]) + (double)(m.z - sz[i]) * (double)(m.z - sz[i]));
		}
		if (flag && sav[i] > dist || !flag && sav[i] <= dist)
		{
			if (ReplayViewer.going)
			{
				if (stg[i] == 1)
				{
					sbln[i] = 0.6F;
					boolean flag1 = false;
					int ai[] = new int[3];
					for (int l = 0; l < 3; l++)
					{
						ai[l] = (int) (255F + 255F * (m.snap[l] / 100F));
						if (ai[l] > 255)
						{
							ai[l] = 255;
						}
						if (ai[l] < 0)
						{
							ai[l] = 0;
						}
					}
					int i1 = (x - t.sx) / 3000;
					if (i1 > t.ncx)
					{
						i1 = t.ncx;
					}
					if (i1 < 0)
					{
						i1 = 0;
					}
					int k1 = (z - t.sz) / 3000;
					if (k1 > t.ncz)
					{
						k1 = t.ncz;
					}
					if (k1 < 0)
					{
						k1 = 0;
					}
					for (int l1 = 0; l1 < t.sect[i1][k1].length; l1++)
					{
						int j2 = t.sect[i1][k1][l1];
						if (Math.abs(t.zy[j2]) == 90 || Math.abs(t.xy[j2]) == 90 || Math.abs(sx[i] - t.x[j2]) >= t.radx[j2] || Math.abs(sz[i] - t.z[j2]) >= t.radz[j2])
						{
							continue;
						}
						if (t.skd[j2] == 0)
						{
							sbln[i] = 0.2F;
						}
						if (t.skd[j2] == 1)
						{
							sbln[i] = 0.4F;
						}
						if (t.skd[j2] == 2)
						{
							sbln[i] = 0.45F;
						}
						for (int j3 = 0; j3 < 3; j3++)
						{
							srgb[i][j3] = (t.c[j2][j3] + ai[j3]) / 2;
						}
						flag1 = true;
					}
					if (!flag1)
					{
						for (int i2 = 0; i2 < 3; i2++)
						{
							srgb[i][i2] = (m.crgrnd[i2] + ai[i2]) / 2;
						}
					}
					float f = (float) (0.10000000000000001D + m.random());
					if (f > 1.0F)
					{
						f = 1.0F;
					}
					scx[i] = (int) (scx[i] * f);
					scz[i] = (int) (scx[i] * f);
					for (int k2 = 0; k2 < 8; k2++)
					{
						smag[i][k2] = osmag[i] * m.random() * 50F;
					}
					for (int l2 = 0; l2 < 8; l2++)
					{
						int k3 = l2 - 1;
						if (k3 == -1)
						{
							k3 = 7;
						}
						int i4 = l2 + 1;
						if (i4 == 8)
						{
							i4 = 0;
						}
						smag[i][l2] = ((smag[i][k3] + smag[i][i4]) / 2.0F + smag[i][l2]) / 2.0F;
					}
					smag[i][6] = smag[i][7];
				}
			}
			
			int j = m.cx + (int) ((sx[i] - m.x - m.cx) * ReplayViewer.cos_m_xz - (sz[i] - m.z - m.cz) * ReplayViewer.sin_m_xz);
			int k = m.cz + (int) ((sx[i] - m.x - m.cx) * ReplayViewer.sin_m_xz + (sz[i] - m.z - m.cz) * ReplayViewer.cos_m_xz);
			int j1 = m.cy + (int) ((sy[i] - m.y - m.cy - smag[i][7]) * ReplayViewer.cos_m_zy - (k - m.cz) * ReplayViewer.sin_m_zy);
			k = m.cz + (int) ((sy[i] - m.y - m.cy - smag[i][7]) * ReplayViewer.sin_m_zy + (k - m.cz) * ReplayViewer.cos_m_zy);
			
			if (ReplayViewer.going)
			{
				sx[i] += scx[i] / (stg[i] + 1);
				sz[i] += scz[i] / (stg[i] + 1);
			}
			
			int ai1[] = new int[8];
			int ai2[] = new int[8];
			ai1[0] = xs((int) (j + smag[i][0] * 0.9238F * 1.5F), k);
			ai2[0] = ys((int) (j1 + smag[i][0] * 0.3826F * 1.5F), k);
			ai1[1] = xs((int) (j + smag[i][1] * 0.9238F * 1.5F), k);
			ai2[1] = ys((int) (j1 - smag[i][1] * 0.3826F * 1.5F), k);
			ai1[2] = xs((int) (j + smag[i][2] * 0.3826F), k);
			ai2[2] = ys((int) (j1 - smag[i][2] * 0.9238F), k);
			ai1[3] = xs((int) (j - smag[i][3] * 0.3826F), k);
			ai2[3] = ys((int) (j1 - smag[i][3] * 0.9238F), k);
			ai1[4] = xs((int) (j - smag[i][4] * 0.9238F * 1.5F), k);
			ai2[4] = ys((int) (j1 - smag[i][4] * 0.3826F * 1.5F), k);
			ai1[5] = xs((int) (j - smag[i][5] * 0.9238F * 1.5F), k);
			ai2[5] = ys((int) (j1 + smag[i][5] * 0.3826F * 1.5F), k);
			ai1[6] = xs((int) (j - smag[i][6] * 0.3826F * 1.7F), k);
			ai2[6] = ys((int) (j1 + smag[i][6] * 0.9238F), k);
			ai1[7] = xs((int) (j + smag[i][7] * 0.3826F * 1.7F), k);
			ai2[7] = ys((int) (j1 + smag[i][7] * 0.9238F), k);
			
			if (ReplayViewer.going)
			{
				for (int i3 = 0; i3 < 7; i3++)
				{
					smag[i][i3] += 5F + m.random() * 15F;
				}
				smag[i][7] = smag[i][6];
			}
			
			rot(ai1, ai2, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 8);
			boolean flag2 = true;
			int l3 = 0;
			int j4 = 0;
			int k4 = 0;
			int l4 = 0;
			for (int i5 = 0; i5 < 8; i5++)
			{
				if (ai2[i5] < m.ih || k < 10)
				{
					l3++;
				}
				if (ai2[i5] > m.h || k < 10)
				{
					j4++;
				}
				if (ai1[i5] < m.iw || k < 10)
				{
					k4++;
				}
				if (ai1[i5] > m.w || k < 10)
				{
					l4++;
				}
			}
			if (k4 == 4 || l3 == 4 || j4 == 4 || l4 == 4)
			{
				flag2 = false;
			}
			if (flag2 && ReplayViewer.iseenx && ReplayViewer.iseeny)
			{
				int j5 = srgb[i][0];
				int k5 = srgb[i][1];
				int l5 = srgb[i][2];
				for (int i6 = 0; i6 < 16; i6++)
				{
					if (sav[i] > m.fade[i6])
					{
						j5 = (j5 * m.fogd + m.cfade[0]) / (m.fogd + 1);
						k5 = (k5 * m.fogd + m.cfade[1]) / (m.fogd + 1);
						l5 = (l5 * m.fogd + m.cfade[2]) / (m.fogd + 1);
					}
				}
				graphics2d.setColor(new Color(j5, k5, l5));
				float f1 = sbln[i] - stg[i] * (sbln[i] / 8F);
				graphics2d.setComposite(AlphaComposite.getInstance(3, f1));
				graphics2d.fillPolygon(ai1, ai2, 8);
				graphics2d.setComposite(AlphaComposite.getInstance(3, 1.0F));
			}
			if (ReplayViewer.going)
			{
				if (stg[i] == 7)
				{
					stg[i] = 0;
				} else
				{
					stg[i]++;
				}
			}
		}
	}

	private void dsprk(Graphics2D graphics2d, boolean flag)
	{
		if (flag && sprk != 0)
		{
			int i = (int) (Math.sqrt(rcx * rcx + rcy * rcy + rcz * rcz) / 10D);
			if (i > 5)
			{
				boolean flag1 = false;
				if (dist < Math.sqrt((double)(m.x + m.cx - srx) * (double)(m.x + m.cx - srx) + (double)(m.y + m.cy - sry) * (double)(m.y + m.cy - sry) + (double)(m.z - srz) * (double)(m.z - srz)))
				{
					flag1 = true;
				}
				if (i > 33)
				{
					i = 33;
				}
				int i1 = 0;
				int k1 = 0;
				do
				{
					if (k1 >= 100)
					{
						break;
					}
					if (rtg[k1] == 0)
					{
						rtg[k1] = 1;
						rbef[k1] = flag1;
						i1++;
					}
					if (i1 == i)
					{
						break;
					}
					k1++;
				} while (true);
			}
		}
		for (int j = 0; j < 100; j++)
		{
			if (rtg[j] == 0 || (!rbef[j] || !flag) && (rbef[j] || flag))
			{
				continue;
			}
			if (ReplayViewer.going)
			{
				if (rtg[j] == 1)
				{
					if (sprk < 5)
					{
						rx[j] = srx + 3 - (int) (m.random() * 6.7000000000000002D);
						ry[j] = sry + 3 - (int) (m.random() * 6.7000000000000002D);
						rz[j] = srz + 3 - (int) (m.random() * 6.7000000000000002D);
					} else
					{
						rx[j] = srx + 10 - (int) (m.random() * 20F);
						ry[j] = sry - (int) (m.random() * 4F);
						rz[j] = srz + 10 - (int) (m.random() * 20F);
					}
					int k = (int) Math.sqrt(rcx * rcx + rcy * rcy + rcz * rcz);
					float f = 0.2F + 0.4F * m.random();
					float f1 = m.random() * m.random() * m.random();
					float f2 = 1.0F;
					if (m.random() > m.random())
					{
						if (m.random() > m.random())
						{
							f2 *= -1F;
						}
						vrx[j] = -((rcx + k * (1.0F - rcx / k) * f1 * f2) * f);
					}
					if (m.random() > m.random())
					{
						if (m.random() > m.random())
						{
							f2 *= -1F;
						}
						if (sprk == 5)
						{
							f2 = 1.0F;
						}
						vry[j] = -((rcy + k * (1.0F - rcy / k) * f1 * f2) * f);
					}
					if (m.random() > m.random())
					{
						if (m.random() > m.random())
						{
							f2 *= -1F;
						}
						vrz[j] = -((rcz + k * (1.0F - rcz / k) * f1 * f2) * f);
					}
				}
				
				rx[j] += vrx[j];
				ry[j] += vry[j];
				rz[j] += vrz[j];
			}
			
			int q = m.cx + (int) ((rx[j] - m.x - m.cx) * ReplayViewer.cos_m_xz - (rz[j] - m.z - m.cz) * ReplayViewer.sin_m_xz);
			int q1 = m.cz + (int) ((rx[j] - m.x - m.cx) * ReplayViewer.sin_m_xz + (rz[j] - m.z - m.cz) * ReplayViewer.cos_m_xz);
			int q2 = m.cy + (int) ((ry[j] - m.y - m.cy) * ReplayViewer.cos_m_zy - (q1 - m.cz) * ReplayViewer.sin_m_zy);
			int j1 = m.cz + (int) ((ry[j] - m.y - m.cy) * ReplayViewer.sin_m_zy + (q1 - m.cz) * ReplayViewer.cos_m_zy);
			int l = m.cx + (int) ((q - m.cx) * ReplayViewer.cos_m_xy - (q2 - m.cy) * ReplayViewer.sin_m_xy);
			int l1 = m.cy + (int) ((q - m.cx) * ReplayViewer.sin_m_xy + (q2 - m.cy) * ReplayViewer.cos_m_xy);
			
			int p = m.cx + (int) ((rx[j] - m.x - m.cx + vrx[j]) * ReplayViewer.cos_m_xz - (rz[j] - m.z - m.cz + vrz[j]) * ReplayViewer.sin_m_xz);
			int p1 = m.cz + (int) ((rx[j] - m.x - m.cx + vrx[j]) * ReplayViewer.sin_m_xz + (rz[j] - m.z - m.cz + vrz[j]) * ReplayViewer.cos_m_xz);
			int p2 = m.cy + (int) ((ry[j] - m.y - m.cy + vry[j]) * ReplayViewer.cos_m_zy - (p1 - m.cz) * ReplayViewer.sin_m_zy);
			int j2 = m.cz + (int) ((ry[j] - m.y - m.cy + vry[j]) * ReplayViewer.sin_m_zy + (p1 - m.cz) * ReplayViewer.cos_m_zy);
			int i2 = m.cx + (int) ((p - m.cx) * ReplayViewer.cos_m_xy - (p2 - m.cy) * ReplayViewer.sin_m_xy);
			int k2 = m.cy + (int) ((p - m.cx) * ReplayViewer.sin_m_xy + (p2 - m.cy) * ReplayViewer.cos_m_xy);
			
			int l2 = xs(l, j1);
			int i3 = ys(l1, j1);
			int j3 = xs(i2, j2);
			int k3 = ys(k2, j2);
			
			if (ReplayViewer.iseenx && ReplayViewer.iseeny)
			{
				int l3 = 255;
				int i4 = 197 - 30 * rtg[j];
				int j4 = 0;
				for (int k4 = 0; k4 < 16; k4++)
				{
					if (j1 > m.fade[k4])
					{
						l3 = (l3 * m.fogd + m.cfade[0]) / (m.fogd + 1);
						i4 = (i4 * m.fogd + m.cfade[1]) / (m.fogd + 1);
						j4 = (j4 * m.fogd + m.cfade[2]) / (m.fogd + 1);
					}
				}
				graphics2d.setColor(new Color(l3, i4, j4));
				graphics2d.drawLine(l2, i3, j3, k3);
			}
			
			if (ReplayViewer.going)
			{
				vrx[j] = vrx[j] * 0.8F;
				vry[j] = vry[j] * 0.8F;
				vrz[j] = vrz[j] * 0.8F;
				if (rtg[j] == 3)
				{
					rtg[j] = 0;
				} else
				{
					rtg[j]++;
				}
			}
		}
		if (sprk != 0)
		{
			sprk = 0;
		}
	}

	private int xs(long i, long j)
	{
		if (j < 50)
		{
			j = 50;
		}
		return (int)((j - m.focus_point) * (m.cx - i) / j + i);
	}

	private int ys(long i, long j)
	{
		if (j < 50)
		{
			j = 50;
		}
		return (int)((j - m.focus_point) * (m.cy - i) / j + i);
	}

	private int py(int i, int j, int k, int l)
	{
		return (i - j) * (i - j) + (k - l) * (k - l);
	}

	private void rot(int ai[], int ai1[], int i, int j, float k, int l)
	{
		if (k != 0)
		{
			for (int i1 = 0; i1 < l; i1++)
			{
				int j1 = ai[i1];
				int k1 = ai1[i1];
				ai[i1] = i + (int) ((j1 - i) * (float)Math.cos(0.0174532925199432957 * k) - (k1 - j) * (float)Math.sin(0.0174532925199432957 * k));
				ai1[i1] = j + (int) ((j1 - i) * (float)Math.sin(0.0174532925199432957 * k) + (k1 - j) * (float)Math.cos(0.0174532925199432957 * k));
			}
		}
	}
	
	private void rot(int ai[], int ai1[], int i, int j, float sin_ang, float cos_ang, int l)
	{
		for (int i1 = 0; i1 < l; i1++)
		{
			int j1 = ai[i1];
			int k1 = ai1[i1];
			ai[i1] = i + (int) ((j1 - i) * cos_ang - (k1 - j) * sin_ang);
			ai1[i1] = j + (int) ((j1 - i) * sin_ang + (k1 - j) * cos_ang);
		}
	}

	Medium m;
	Trackers t;
	final Plane[] p;
	int npl;
	int x;
	int y;
	int z;
	int xz;
	int xy;
	int zy;
	int wxz;
	int wzy;
	private transient double dist;
	private int maxR;
	private int disp;
	private int disline;
	private boolean shadow;
	private boolean noline;
	private boolean decor;
	float grounded;
	private int grat;
	final int[] keyx;
	final int[] keyz;
	private int sprkat;
	private int txy[];
	private int tzy[];
	private int tc[][];
	private int tradx[];
	private int tradz[];
	private int trady[];
	private int tx[];
	private int ty[];
	private int tz[];
	private int skd[];
	private int dam[];
	private boolean notwall[];
	private final int tnt;
	int stg[];
	int sx[];
	int sy[];
	int sz[];
	int scx[];
	int scz[];
	float osmag[];
	transient double sav[];
	private float smag[][];
	private int srgb[][];
	private float sbln[];
	int ust;
	int srx;
	int sry;
	int srz;
	float rcx;
	float rcy;
	float rcz;
	int sprk;
	private int rtg[];
	private boolean rbef[];
	private int rx[];
	private int ry[];
	private int rz[];
	private float vrx[];
	private float vry[];
	private float vrz[];
	private final boolean elec;
	private final boolean roted;
	private final int[] edl;
	private final int[] edr;
	private final int[] elc = { 0, 0, 0, 0 };
	boolean fix;
	int fcnt;
	final int checkpoint;
	final int[] fcol = { 0, 0, 0 };
	final int[] scol = { 0, 0, 0 };
}
