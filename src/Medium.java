import java.awt.Color;
import java.awt.Graphics2D;

class Medium
{
	float random()
	{
		if (cntrn == 0)
		{
			for (int i = 0; i < 3; i++)
			{
				rand[i] = (int) (10D * Math.random());
                diup[i] = Math.random() <= Math.random();
			}
			cntrn = 20;
		} else
		{
			cntrn--;
		}
		for (int j = 0; j < 3; j++)
		{
			if (diup[j])
			{
				rand[j]++;
				if (rand[j] == 10)
				{
					rand[j] = 0;
				}
				continue;
			}
			rand[j]--;
			if (rand[j] == -1)
			{
				rand[j] = 9;
			}
		}
		trn++;
		if (trn == 3)
		{
			trn = 0;
		}
		return rand[trn] / 10F;
	}
	
	void d(Graphics2D graphics2d)
	{
		graphics2d.rotate(xy * 0.017453292519943295D, cx, cy);
		nsp = 0;
		int loops = 0;
		
		if (xz > 359)
			xz -= 360;
		if (xz < 0)
			xz += 360;
		
		if (zy > 359)
			zy -= 360;
		if (zy < 0)
			zy += 360;
		
		if (xy > 359)
			xy -= 360;
		if (xy < 0)
			xy += 360;
		
		if(y + cy > 250)
			y = 250 - cy;
		
		for(; zy >= 90; zy -= 180) { loops++; }
        for(; zy < -90; zy += 180) { loops--; }
		ground = 250 - y;
		
		float t_cos = (float)StrictMath.cos(0.0174532925199432957 * zy);
		float t_sin = (float)StrictMath.sin(0.0174532925199432957 * zy);
		
		int ai[] = new int[4];
		int ai1[] = new int[4];
		int i = cgrnd[0];
		int j = cgrnd[1];
		int k = cgrnd[2];
		int l = crgrnd[0];
		int i1 = crgrnd[1];
		int j1 = crgrnd[2];
		int k1;
		
		if(loops % 2 == 0)
			k1 = h + cx;
		else
			k1 = ih - cx;
		
		for (int l1 = 0; l1 < 16; l1++)
		{
			int j2 = fade[l1];
			int l2;
			
			if(loops % 2 == 0)
			{
				l2 = ground;
				if (zy != 0)
				{
					l2 = cy + (int) ((ground - cy) * t_cos - (float)(fade[l1] - cz) * t_sin);
					j2 = cz + (int) ((ground - cy) * t_sin + (float)(fade[l1] - cz) * t_cos);
				}
			} else
			{
				l2 = -ground + h;
                if(zy != 0)
                {
                    l2 = cy + (int)((float)(-ground + h - cy) * t_cos - (float)(fade[l1] - cz) * t_sin);
                    j2 = cz + (int)((float)(-ground + h - cy) * t_sin + (float)(fade[l1] - cz) * t_cos);
                }
			}
			ai[0] = iw;
			ai1[0] = ys(l2, j2);
			
			if (ai1[0] < ih - cx)
			{
				ai1[0] = ih - cx;
			}
			if (ai1[0] > h + cx)
			{
				ai1[0] = h + cx;
			}
			ai[1] = iw - cx;
			ai1[1] = k1;
			ai[2] = w + cx;
			ai1[2] = k1;
			ai[3] = w + cx;
			ai1[3] = ai1[0];
			k1 = ai1[0];
			if (l1 > 0)
			{
				l = (l * 7 + cfade[0]) / 8;
				i1 = (i1 * 7 + cfade[1]) / 8;
				j1 = (j1 * 7 + cfade[2]) / 8;
				if (l1 < 3)
				{
					i = (i * 7 + cfade[0]) / 8;
					j = (j * 7 + cfade[1]) / 8;
					k = (k * 7 + cfade[2]) / 8;
				} else
				{
					i = l;
					j = i1;
					k = j1;
				}
			}
			graphics2d.setColor(new Color(i, j, k));
			graphics2d.fillPolygon(ai, ai1, 4);
		}
		if (lightn != -1 && lton)
		{
			if (lightn < 16)
			{
				if (lilo > lightn + 217)
				{
					lilo -= 3;
				} else
				{
					lightn = (int) (16F + 16F * random());
				}
			} else if (lilo < lightn + 217)
			{
				lilo += 7;
			} else
			{
				lightn = (int) (16F * random());
			}
			csky[0] = (int) (lilo + lilo * (snap[0] / 100F));
			if (csky[0] > 255)
			{
				csky[0] = 255;
			}
			if (csky[0] < 0)
			{
				csky[0] = 0;
			}
			csky[1] = (int) (lilo + lilo * (snap[1] / 100F));
			if (csky[1] > 255)
			{
				csky[1] = 255;
			}
			if (csky[1] < 0)
			{
				csky[1] = 0;
			}
			csky[2] = (int) (lilo + lilo * (snap[2] / 100F));
			if (csky[2] > 255)
			{
				csky[2] = 255;
			}
			if (csky[2] < 0)
			{
				csky[2] = 0;
			}
		}
		i = csky[0];
		j = csky[1];
		k = csky[2];
		int i2 = i;
		int k2 = j;
		int i3 = k;
		
		int j3;
		int k3;
		
		if(loops % 2 == 0)
		{
			j3 = cy + (int) ((skyline - 700 - cy) * t_cos - (7000 - cz) * t_sin);
			k3 = cz + (int) ((skyline - 700 - cy) * t_sin + (7000 - cz) * t_cos);
		} else
		{
			j3 = cy + (int) ((-skyline + 700 + h - cy) * t_cos - (7000 - cz) * t_sin);
			k3 = cz + (int) ((-skyline + 700 + h - cy) * t_sin + (7000 - cz) * t_cos);
		}
		
		j3 = ys(j3, k3);
		
		int l3;
		
		if(loops % 2 == 0)
			l3 = ih - cx;
		else
			l3 = h + cx;
		
		for (int i4 = 0; i4 < 16; i4++)
		{
			int k4 = fade[i4];
			int i5;
			
			if (loops % 2 == 0)
			{
				i5 = skyline;
				if (zy != 0)
				{
					i5 = cy + (int) ((skyline - cy) * t_cos - (fade[i4] - cz) * t_sin);
					k4 = cz + (int) ((skyline - cy) * t_sin + (fade[i4] - cz) * t_cos);
				}
			} else
			{
				i5 = -skyline + h;
				if (zy != 0)
				{
					i5 = cy + (int) ((-skyline + h - cy) * t_cos - (fade[i4] - cz) * t_sin);
					k4 = cz + (int) ((-skyline + h - cy) * t_sin + (fade[i4] - cz) * t_cos);
				}
			}
			
			ai[0] = iw - cx;
			ai1[0] = ys(i5, k4);
			if (ai1[0] < ih - cx)
			{
				ai1[0] = ih - cx;
			}
			if (ai1[0] > h + cx)
			{
				ai1[0] = h + cx;
			}
			ai[1] = iw - cx;
			ai1[1] = l3;
			ai[2] = w + cx;
			ai1[2] = l3;
			ai[3] = w + cx;
			ai1[3] = ai1[0];
			l3 = ai1[0];
			if (i4 > 0)
			{
				i = (i * 7 + cfade[0]) / 8;
				j = (j * 7 + cfade[1]) / 8;
				k = (k * 7 + cfade[2]) / 8;
			}
			graphics2d.setColor(new Color(i, j, k));
			graphics2d.fillPolygon(ai, ai1, 4);
		}
		ai[0] = iw - cx;
		ai1[0] = l3;
		ai[1] = iw - cx;
		ai1[1] = k1;
		ai[2] = w + cx;
		ai1[2] = k1;
		ai[3] = w + cx;
		ai1[3] = l3;
		
		float f = (Math.abs(y) - 250F) / (fade[0] * 2);
		if (f < 0.0F)
		{
			f = 0.0F;
		}
		if (f > 1.0F)
		{
			f = 1.0F;
		}
		i = (int) ((i * (1.0F - f) + l * (1.0F + f)) / 2.0F);
		j = (int) ((j * (1.0F - f) + i1 * (1.0F + f)) / 2.0F);
		k = (int) ((k * (1.0F - f) + j1 * (1.0F + f)) / 2.0F);
		graphics2d.setColor(new Color(i, j, k));
		graphics2d.fillPolygon(ai, ai1, 4);
		
		if (resdown != 2)
		{
			for (int j4 = 1; j4 < 20; j4++)
			{
				int l4 = 7000;
				int j5;
				
				if (loops % 2 == 0)
				{
					j5 = skyline - 700 - j4 * 70;
					if (zy != 0 && j4 != 19)
					{
						j5 = cy + (int) ((skyline - 700 - j4 * 70 - cy) * t_cos - (7000 - cz) * t_sin);
						l4 = cz + (int) ((skyline - 700 - j4 * 70 - cy) * t_sin + (7000 - cz) * t_cos);
					}
				} else
				{
					j5 = -skyline + 700 + j4 * 70 + h;
					if (zy != 0 && j4 != 19)
					{
						j5 = cy + (int) ((-skyline + 700 + j4 * 70 + h - cy) * t_cos - (7000 - cz) * t_sin);
						l4 = cz + (int) ((-skyline + 700 + j4 * 70 + h - cy) * t_sin + (7000 - cz) * t_cos);
					}
				}
				
				ai[0] = iw - cx;
				if (j4 != 19)
				{
					ai1[0] = ys(j5, l4);
					if (ai1[0] > h + cx)
					{
						ai1[0] = h + cx;
					}
					if (ai1[0] < ih - cx)
					{
						ai1[0] = ih - cx;
					}
				} else
				{
					if (loops % 2 == 0)
						ai1[0] = ih - cx;
					else
						ai1[0] = h + cx;
				}
				ai[1] = iw - cx;
				ai1[1] = j3;
				ai[2] = w + cx;
				ai1[2] = j3;
				ai[3] = w + cx;
				ai1[3] = ai1[0];
				j3 = ai1[0];
				i2 = (int) (i2 * 0.99099999999999999D);
				k2 = (int) (k2 * 0.99099999999999999D);
				i3 = (int) (i3 * 0.998D);
				graphics2d.setColor(new Color(i2, k2, i3));
				graphics2d.fillPolygon(ai, ai1, 4);
			}
			zy += 180 * loops;
			graphics2d.rotate(-xy * 0.017453292519943295D, cx, cy);
			if (lightson)
			{
				drawstars(graphics2d);
			}
			drawmountains(graphics2d);
			drawclouds(graphics2d);
		}
		groundpolys(graphics2d);
		
		if (ReplayViewer.going)
		{
			if (noelec != 0)
			{
				noelec--;
			}
			if (cpflik)
			{
				cpflik = false;
			} else
			{
				cpflik = true;
				elecr = random() * 15F - 6F;
			}
		}
	}
	
	void fadfrom(int i)
	{
		for (int j = 1; j < 17; j++)
		{
			fade[j - 1] = i / 2 * (j + 1);
		}
	}
	
	private int ys(long i, long j)
	{
		if (j < cz)
		{
			j = cz;
		}
		return (int)((j - focus_point) * (cy - i) / j + i);
	}
	
	private void groundpolys(Graphics2D graphics2d)
	{
		int sx = Math.round((x - sgpx) * 0.00083333333F);
		int sz = Math.round((z - sgpz) * 0.00083333333F);
		int rg = Math.round(ReplayViewer.drawGround.getValue());
		
		int i = sx - rg;
		int j = sx + rg;
		
		if(i < 0)
		{
			i = 0;
			j = rg + rg;
			
			if(j > nrw)
				j = nrw;
		} else if (j > nrw)
		{
			j = nrw;
			i = nrw - rg - rg;
			
			if(i < 0)
				i = 0;
		}
		
		int k = sz - rg;
		int l = sz + rg;
		
		if(k < 0)
		{
			k = 0;
			l = rg + rg;
			
			if(l > ncl)
				l = ncl;
		} else if (l > ncl)
		{
			l = ncl;
			k = ncl - rg - rg;
			
			if(k < 0)
				k = 0;
		}
		
		int ai[][] = new int[j - i][l - k];
		for (int i1 = i; i1 < j; i1++)
		{
			for (int k1 = k; k1 < l; k1++)
			{
				ai[i1 - i][k1 - k] = 0;
				int i2 = i1 + k1 * nrw;
				int f7 = cx + (int) ((cgpx[i2] - x - cx) * ReplayViewer.cos_m_xz - (cgpz[i2] - z - cz) * ReplayViewer.sin_m_xz);
				int l2 = cz + (int) ((cgpx[i2] - x - cx) * ReplayViewer.sin_m_xz + (cgpz[i2] - z - cz) * ReplayViewer.cos_m_xz);
				int d2 = cy + (int) ((250 - y - cy) * ReplayViewer.cos_m_zy - (l2 - cz) * ReplayViewer.sin_m_zy);
				int i3 = cz + (int) ((250 - y - cy) * ReplayViewer.sin_m_zy + (l2 - cz) * ReplayViewer.cos_m_zy);
				int k2 = cx + (int) ((f7 - cx) * ReplayViewer.cos_m_xy - (d2 - cy) * ReplayViewer.sin_m_xy);
				int m2 = cy + (int) ((f7 - cx) * ReplayViewer.sin_m_xy + (d2 - cy) * ReplayViewer.cos_m_xy);
				
				int f2 = xs(k2 + pmx[i2], i3);
				int f3 = xs(k2 - pmx[i2], i3);
				int f4 = ys(m2 + pmx[i2], i3);
				int f5 = ys(m2 - pmx[i2], i3);
				
				if ((f2 < 0 && f3 < 0) || (f2 > w && f3 > w) || (f4 < 0 && f5 < 0) || (f4 > h && f5 > h))
				{
					continue;
				}
				ai[i1 - i][k1 - k] = i3;
				int ai4[] = new int[8];
				int ai6[] = new int[8];
				int ai8[] = new int[8];
				for (int l3 = 0; l3 < 8; l3++)
				{
					ai4[l3] = (int) (ogpx[i2][l3] * pvr[i2][l3] + cgpx[i2] - x);
					ai6[l3] = (int) (ogpz[i2][l3] * pvr[i2][l3] + cgpz[i2] - z);
					ai8[l3] = ground;
				}
				rot(ai4, ai6, cx, cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, 8);
				rot(ai8, ai6, cy, cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, 8);
				rot(ai4, ai8, cx, cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 8);
				int ai9[] = new int[8];
				int ai10[] = new int[8];
				int k4 = 0;
				int i5 = 0;
				int j5 = 0;
				int i6 = 0;
				boolean flag1 = true;
				for (int l6 = 0; l6 < 8; l6++)
				{
					ai9[l6] = xs(ai4[l6], ai6[l6]);
					ai10[l6] = ys(ai8[l6], ai6[l6]);
					if (ai10[l6] < 0 || ai6[l6] < 10)
					{
						k4++;
					}
					if (ai10[l6] > h || ai6[l6] < 10)
					{
						i5++;
					}
					if (ai9[l6] < 0 || ai6[l6] < 10)
					{
						j5++;
					}
					if (ai9[l6] > w || ai6[l6] < 10)
					{
						i6++;
					}
				}
				if (j5 == 8 || k4 == 8 || i5 == 8 || i6 == 8)
				{
					flag1 = false;
				}
				if (!flag1)
				{
					continue;
				}
				int i7 = (int) ((cpol[0] * pcv[i2] + cgrnd[0]) / 2.0F);
				int j7 = (int) ((cpol[1] * pcv[i2] + cgrnd[1]) / 2.0F);
				int k7 = (int) ((cpol[2] * pcv[i2] + cgrnd[2]) / 2.0F);
				for (int j17 = 0; j17 < 16; j17++)
				{
					if (i3 - pmx[i2] > fade[j17])
					{
						i7 = (i7 * 7 + cfade[0]) / 8;
						j7 = (j7 * 7 + cfade[1]) / 8;
						k7 = (k7 * 7 + cfade[2]) / 8;
					}
				}
				graphics2d.setColor(new Color(i7, j7, k7));
				graphics2d.fillPolygon(ai9, ai10, 8);
			}
		}
		for (int j1 = i; j1 < j; j1++)
		{
			for (int l1 = k; l1 < l; l1++)
			{
				if (ai[j1 - i][l1 - k] == 0)
				{
					continue;
				}
				int j2 = j1 + l1 * nrw;
				int ai1[] = new int[8];
				int ai2[] = new int[8];
				int ai3[] = new int[8];
				for (int j3 = 0; j3 < 8; j3++)
				{
					ai1[j3] = ogpx[j2][j3] + cgpx[j2] - x;
					ai2[j3] = ogpz[j2][j3] + cgpz[j2] - z;
					ai3[j3] = ground;
				}
				rot(ai1, ai2, cx, cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, 8);
				rot(ai3, ai2, cy, cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, 8);
				rot(ai1, ai3, cx, cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 8);
				int ai5[] = new int[8];
				int ai7[] = new int[8];
				int k3 = 0;
				int i4 = 0;
				int j4 = 0;
				int l4 = 0;
				boolean flag = true;
				for (int k5 = 0; k5 < 8; k5++)
				{
					ai5[k5] = xs(ai1[k5], ai2[k5]);
					ai7[k5] = ys(ai3[k5], ai2[k5]);
					if (ai7[k5] < 0 || ai2[k5] < 10)
					{
						k3++;
					}
					if (ai7[k5] > h || ai2[k5] < 10)
					{
						i4++;
					}
					if (ai5[k5] < 0 || ai2[k5] < 10)
					{
						j4++;
					}
					if (ai5[k5] > w || ai2[k5] < 10)
					{
						l4++;
					}
				}
				if (j4 == 8 || k3 == 8 || i4 == 8 || l4 == 8)
				{
					flag = false;
				}
				if (!flag)
				{
					continue;
				}
				int l5 = (int) (cpol[0] * pcv[j2]);
				int j6 = (int) (cpol[1] * pcv[j2]);
				int k6 = (int) (cpol[2] * pcv[j2]);
				for (int j17 = 0; j17 < 16; j17++)
				{
					if (ai[j1 - i][l1 - k] - pmx[j2] > fade[j17])
					{
						l5 = (l5 * 7 + cfade[0]) / 8;
						j6 = (j6 * 7 + cfade[1]) / 8;
						k6 = (k6 * 7 + cfade[2]) / 8;
					}
				}
				graphics2d.setColor(new Color(l5, j6, k6));
				graphics2d.fillPolygon(ai5, ai7, 8);
			}
		}
	}
	
	private void drawclouds(Graphics2D graphics2d)
	{
		for (int i = 0; i < noc; i++)
		{
			int m = cx + (int) ((clx[i] - x / 20 - cx) * ReplayViewer.cos_m_xz - (clz[i] - z / 20 - cz) * ReplayViewer.sin_m_xz);
			int k = cz + (int) ((clx[i] - x / 20 - cx) * ReplayViewer.sin_m_xz + (clz[i] - z / 20 - cz) * ReplayViewer.cos_m_xz);
			int q = cy + (int) ((cldd[4] - y / 20 - cy) * ReplayViewer.cos_m_zy - (k - cz) * ReplayViewer.sin_m_zy);
			int l = cz + (int) ((cldd[4] - y / 20 - cy) * ReplayViewer.sin_m_zy + (k - cz) * ReplayViewer.cos_m_zy);
			int j = cx + (int) ((m - cx) * ReplayViewer.cos_m_xy - (q - cy) * ReplayViewer.sin_m_xy);
			int n = cy + (int) ((m - cx) * ReplayViewer.sin_m_xy + (q - cy) * ReplayViewer.cos_m_xy);
			int i1 = xs(j + (int)(cmx[i] * 1.5), l);
			int j1 = xs(j - (int)(cmx[i] * 1.5), l);
			int p = ys(n + 900, l);
			int u = ys(n - 900, l);
			if ((i1 < 0 && j1 < 0) || (i1 > w && j1 > w) || (p < 0 && u < 0) || (p > h && u > h))
			{
				continue;
			}
			int ai[][] = new int[3][12];
			int ai1[][] = new int[3][12];
			int ai2[][] = new int[3][12];
			int ai3[] = new int[12];
			int ai4[] = new int[12];
			int k1;
			int l1;
			int i2;
			int j2;
			boolean flag;
			int k2;
			int l2;
			int i3;
			for (int j3 = 0; j3 < 3; j3++)
			{
				for (int k4 = 0; k4 < 12; k4++)
				{
					ai[j3][k4] = clax[i][j3][k4] + clx[i] - x / 20;
					ai2[j3][k4] = claz[i][j3][k4] + clz[i] - z / 20;
					ai1[j3][k4] = clay[i][j3][k4] + cldd[4] - y / 20;
				}
				rot(ai[j3], ai2[j3], cx, cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, 12);
				rot(ai1[j3], ai2[j3], cy, cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, 12);
				rot(ai[j3], ai1[j3], cx, cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 12);
			}
			for (int k3 = 0; k3 < 12; k3 += 2)
			{
				k1 = 0;
				l1 = 0;
				i2 = 0;
				j2 = 0;
				flag = true;
				k2 = 0;
				l2 = 0;
				i3 = 0;
				for (int l4 = 0; l4 < 6; l4++)
				{
					int i6 = 0;
					byte byte0 = 1;
					if (l4 == 0)
					{
						i6 = k3;
					}
					if (l4 == 1)
					{
						i6 = k3 + 1;
						if (i6 >= 12)
						{
							i6 -= 12;
						}
					}
					if (l4 == 2)
					{
						i6 = k3 + 2;
						if (i6 >= 12)
						{
							i6 -= 12;
						}
					}
					if (l4 == 3)
					{
						i6 = k3 + 2;
						if (i6 >= 12)
						{
							i6 -= 12;
						}
						byte0 = 2;
					}
					if (l4 == 4)
					{
						i6 = k3 + 1;
						if (i6 >= 12)
						{
							i6 -= 12;
						}
						byte0 = 2;
					}
					if (l4 == 5)
					{
						i6 = k3;
						byte0 = 2;
					}
					ai3[l4] = xs(ai[byte0][i6], ai2[byte0][i6]);
					ai4[l4] = ys(ai1[byte0][i6], ai2[byte0][i6]);
					l2 += ai[byte0][i6];
					k2 += ai1[byte0][i6];
					i3 += ai2[byte0][i6];
					if (ai4[l4] < 0 || ai2[0][l4] < 10)
					{
						k1++;
					}
					if (ai4[l4] > h || ai2[0][l4] < 10)
					{
						l1++;
					}
					if (ai3[l4] < 0 || ai2[0][l4] < 10)
					{
						i2++;
					}
					if (ai3[l4] > w || ai2[0][l4] < 10)
					{
						j2++;
					}
				}
				if (i2 == 6 || k1 == 6 || l1 == 6 || j2 == 6)
				{
					flag = false;
				}
				if (!flag)
				{
					continue;
				}
				l2 /= 6;
				k2 /= 6;
				i3 /= 6;
				int i5 = (int)Math.sqrt((long)(cy - k2) * (long)(cy - k2) + (long)(cx - l2) * (long)(cx - l2) + (long)i3 * (long)i3);
				if (i5 >= fade[7])
				{
					continue;
				}
				int j6 = clc[i][1][k3 / 2][0];
				int j7 = clc[i][1][k3 / 2][1];
				int j8 = clc[i][1][k3 / 2][2];
				for (int i9 = 0; i9 < 16; i9++)
				{
					if (i5 > fade[i9])
					{
						j6 = (j6 * fogd + cfade[0]) / (fogd + 1);
						j7 = (j7 * fogd + cfade[1]) / (fogd + 1);
						j8 = (j8 * fogd + cfade[2]) / (fogd + 1);
					}
				}
				graphics2d.setColor(new Color(j6, j7, j8));
				graphics2d.fillPolygon(ai3, ai4, 6);
			}
			for (int l3 = 0; l3 < 12; l3 += 2)
			{
				k1 = 0;
				l1 = 0;
				i2 = 0;
				j2 = 0;
				flag = true;
				k2 = 0;
				l2 = 0;
				i3 = 0;
				for (int j5 = 0; j5 < 6; j5++)
				{
					int k6 = 0;
					int k7 = 0;
					if (j5 == 0)
					{
						k6 = l3;
					}
					if (j5 == 1)
					{
						k6 = l3 + 1;
						if (k6 >= 12)
						{
							k6 -= 12;
						}
					}
					if (j5 == 2)
					{
						k6 = l3 + 2;
						if (k6 >= 12)
						{
							k6 -= 12;
						}
					}
					if (j5 == 3)
					{
						k6 = l3 + 2;
						if (k6 >= 12)
						{
							k6 -= 12;
						}
						k7 = 1;
					}
					if (j5 == 4)
					{
						k6 = l3 + 1;
						if (k6 >= 12)
						{
							k6 -= 12;
						}
						k7 = 1;
					}
					if (j5 == 5)
					{
						k6 = l3;
						k7 = 1;
					}
					ai3[j5] = xs(ai[k7][k6], ai2[k7][k6]);
					ai4[j5] = ys(ai1[k7][k6], ai2[k7][k6]);
					l2 += ai[k7][k6];
					k2 += ai1[k7][k6];
					i3 += ai2[k7][k6];
					if (ai4[j5] < 0 || ai2[0][j5] < 10)
					{
						k1++;
					}
					if (ai4[j5] > h || ai2[0][j5] < 10)
					{
						l1++;
					}
					if (ai3[j5] < 0 || ai2[0][j5] < 10)
					{
						i2++;
					}
					if (ai3[j5] > w || ai2[0][j5] < 10)
					{
						j2++;
					}
				}
				if (i2 == 6 || k1 == 6 || l1 == 6 || j2 == 6)
				{
					flag = false;
				}
				if (!flag)
				{
					continue;
				}
				l2 /= 6;
				k2 /= 6;
				i3 /= 6;
				int k5 = (int)Math.sqrt((long)(cy - k2) * (long)(cy - k2) + (long)(cx - l2) * (long)(cx - l2) + (long)i3 * (long)i3);
				if (k5 >= fade[7])
				{
					continue;
				}
				int l6 = clc[i][0][l3 / 2][0];
				int l7 = clc[i][0][l3 / 2][1];
				int k8 = clc[i][0][l3 / 2][2];
				for (int j9 = 0; j9 < 16; j9++)
				{
					if (k5 > fade[j9])
					{
						l6 = (l6 * fogd + cfade[0]) / (fogd + 1);
						l7 = (l7 * fogd + cfade[1]) / (fogd + 1);
						k8 = (k8 * fogd + cfade[2]) / (fogd + 1);
					}
				}
				graphics2d.setColor(new Color(l6, l7, k8));
				graphics2d.fillPolygon(ai3, ai4, 6);
			}
			k1 = 0;
			l1 = 0;
			i2 = 0;
			j2 = 0;
			flag = true;
			k2 = 0;
			l2 = 0;
			i3 = 0;
			for (int i4 = 0; i4 < 12; i4++)
			{
				ai3[i4] = xs(ai[0][i4], ai2[0][i4]);
				ai4[i4] = ys(ai1[0][i4], ai2[0][i4]);
				l2 += ai[0][i4];
				k2 += ai1[0][i4];
				i3 += ai2[0][i4];
				if (ai4[i4] < 0 || ai2[0][i4] < 10)
				{
					k1++;
				}
				if (ai4[i4] > h || ai2[0][i4] < 10)
				{
					l1++;
				}
				if (ai3[i4] < 0 || ai2[0][i4] < 10)
				{
					i2++;
				}
				if (ai3[i4] > w || ai2[0][i4] < 10)
				{
					j2++;
				}
			}
			if (i2 == 12 || k1 == 12 || l1 == 12 || j2 == 12)
			{
				flag = false;
			}
			if (!flag)
			{
				continue;
			}
			l2 /= 12;
			k2 /= 12;
			i3 /= 12;
			int j4 = (int)Math.sqrt((long)(cy - k2) * (long)(cy - k2) + (long)(cx - l2) * (long)(cx - l2) + (long)i3 * (long)i3);
			if (j4 >= fade[7])
			{
				continue;
			}
			int l5 = clds[0];
			int i7 = clds[1];
			int i8 = clds[2];
			for (int l8 = 0; l8 < 16; l8++)
			{
				if (j4 > fade[l8])
				{
					l5 = (l5 * fogd + cfade[0]) / (fogd + 1);
					i7 = (i7 * fogd + cfade[1]) / (fogd + 1);
					i8 = (i8 * fogd + cfade[2]) / (fogd + 1);
				}
			}
			graphics2d.setColor(new Color(l5, i7, i8));
			graphics2d.fillPolygon(ai3, ai4, 12);
		}
	}
	
	private void drawmountains(Graphics2D graphics2d)
	{
		for (int i = 0; i < nmt; i++)
		{
			int j = mrd[i];
			
			int k8 = cx + (int) ((mtx[j][0] - x / 30 - cx) * ReplayViewer.cos_m_xz - (mtz[j][0] - z / 30 - cz) * ReplayViewer.sin_m_xz);
			int l = cz + (int) ((mtx[j][0] - x / 30 - cx) * ReplayViewer.sin_m_xz + (mtz[j][0] - z / 30 - cz) * ReplayViewer.cos_m_xz);
			int m1 = cy + (int) ((mty[j][0] - y / 30 - cy) * ReplayViewer.cos_m_zy - (l - cz) * ReplayViewer.sin_m_zy);
			int i1 = cz + (int) ((mty[j][0] - y / 30 - cy) * ReplayViewer.sin_m_zy + (l - cz) * ReplayViewer.cos_m_zy);
			int k = cx + (int) ((k8 - cx) * ReplayViewer.cos_m_xy - (m1 - cy) * ReplayViewer.sin_m_xy);
			int p = cy + (int) ((k8 - cx) * ReplayViewer.sin_m_xy + (m1 - cy) * ReplayViewer.cos_m_xy);
			
			int j8 = cx + (int) ((mtx[j][nmv[j] - 1] - x / 30 - cx) * ReplayViewer.cos_m_xz - (mtz[j][nmv[j] - 1] - z / 30 - cz) * ReplayViewer.sin_m_xz);
			int k1 = cz + (int) ((mtx[j][nmv[j] - 1] - x / 30 - cx) * ReplayViewer.sin_m_xz + (mtz[j][nmv[j] - 1] - z / 30 - cz) * ReplayViewer.cos_m_xz);
			int m2 = cy + (int) ((mty[j][nmv[j] - 1] - y / 30 - cy) * ReplayViewer.cos_m_zy - (k1 - cz) * ReplayViewer.sin_m_zy);
			int l1 = cz + (int) ((mty[j][nmv[j] - 1] - y / 30 - cy) * ReplayViewer.sin_m_zy + (k1 - cz) * ReplayViewer.cos_m_zy);
			int j1 = cx + (int) ((j8 - cx) * ReplayViewer.cos_m_xy - (m2 - cy) * ReplayViewer.sin_m_xy);
			int p2 = cy + (int) ((j8 - cx) * ReplayViewer.sin_m_xy + (m2 - cy) * ReplayViewer.cos_m_xy);
			
			if ((xs(j1, l1) < 0 && xs(k, i1) < 0) || (xs(j1, l1) > w && xs(k, i1) > w) || (ys(p2, l1) < 0 && ys(p, i1) < 0) || (ys(p2, l1) > w && ys(p, i1) > w))
			{
				continue;
			}
			int ai[] = new int[nmv[j] * 2];
			int ai1[] = new int[nmv[j] * 2];
			int ai2[] = new int[nmv[j] * 2];
			for (int i2 = 0; i2 < nmv[j] * 2; i2++)
			{
				ai[i2] = mtx[j][i2] - x / 30;
				ai1[i2] = mty[j][i2] - y / 30;
				ai2[i2] = mtz[j][i2] - z / 30;
			}
			int j2 = (int)Math.sqrt((long)ai[nmv[j] / 4] * (long)ai[nmv[j] / 4] + (long)ai2[nmv[j] / 4] * (long)ai2[nmv[j] / 4]);
			rot(ai, ai2, cx, cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, nmv[j] * 2);
			rot(ai1, ai2, cy, cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, nmv[j] * 2);
			rot(ai, ai1, cx, cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, nmv[j] * 2);
			int ai3[] = new int[4];
			int ai4[] = new int[4];
			for (int k3 = 0; k3 < nmv[j] - 1; k3++)
			{
				int k2 = 0;
				int l2 = 0;
				int i3 = 0;
				int j3 = 0;
				boolean flag5 = true;
				for (int l3 = 0; l3 < 4; l3++)
				{
					int i4 = l3 + k3;
					if (l3 == 2)
					{
						i4 = k3 + nmv[j] + 1;
					}
					if (l3 == 3)
					{
						i4 = k3 + nmv[j];
					}
					ai3[l3] = xs(ai[i4], ai2[i4]);
					ai4[l3] = ys(ai1[i4], ai2[i4]);
					if (ai4[l3] < 0 || ai2[i4] < 10)
					{
						k2++;
					}
					if (ai4[l3] > h || ai2[i4] < 10)
					{
						l2++;
					}
					if (ai3[l3] < 0 || ai2[i4] < 10)
					{
						i3++;
					}
					if (ai3[l3] > w || ai2[i4] < 10)
					{
						j3++;
					}
				}
				if (i3 == 4 || k2 == 4 || l2 == 4 || j3 == 4)
				{
					flag5 = false;
				}
				if (!flag5)
				{
					continue;
				}
				float f = j2 / 2500F + (8000F - fade[0]) / 1000F - 2.0F - (Math.abs(y) - 250F) / 5000F;
				if (f <= 0.0F || f >= 10F)
				{
					continue;
				}
				if (f < 3.5D)
				{
					f = 3.5F;
				}
				int j4 = (int) ((mtc[j][k3][0] + cgrnd[0] + csky[0] * f + cfade[0] * f) / (2.0F + f * 2.0F));
				int k4 = (int) ((mtc[j][k3][1] + cgrnd[1] + csky[1] * f + cfade[1] * f) / (2.0F + f * 2.0F));
				int l4 = (int) ((mtc[j][k3][2] + cgrnd[2] + csky[2] * f + cfade[2] * f) / (2.0F + f * 2.0F));
				graphics2d.setColor(new Color(j4, k4, l4));
				graphics2d.fillPolygon(ai3, ai4, 4);
			}
		}
	}
	
	private void drawstars(Graphics2D graphics2d)
	{
		for (int i = 0; i < nst; i++)
		{
			int j = cx + (int) (stx[i] * ReplayViewer.cos_m_xz - stz[i] * ReplayViewer.sin_m_xz);
			int k = cz + (int) (stx[i] * ReplayViewer.sin_m_xz + stz[i] * ReplayViewer.cos_m_xz);
			int l = cy + (int) (-200F * ReplayViewer.cos_m_zy - k * ReplayViewer.sin_m_zy);
			int i1 = cz + (int) (-200F * ReplayViewer.sin_m_zy + k * ReplayViewer.cos_m_zy);
			int m1 = cx + (int) ((j - cx) * ReplayViewer.cos_m_xy - (l - cy) * ReplayViewer.sin_m_xy);
			int n1 = cy + (int) ((j - cx) * ReplayViewer.sin_m_xy + (l - cy) * ReplayViewer.cos_m_xy);
			j = xs(m1, i1);
			l = ys(n1, i1);
			if (j + 5 < iw || j - 5 > w || l + 5 < ih || l - 5 > h)
			{
				continue;
			}
			if (twn[i] == 0)
			{
				int j1 = (int) (3D * Math.random());
				if (j1 >= 3)
				{
					j1 = 0;
				}
				if (j1 <= -1)
				{
					j1 = 2;
				}
				int l1 = j1 + 1;
				if (Math.random() > Math.random())
				{
					l1 = j1 - 1;
				}
				if (l1 == 3)
				{
					l1 = 0;
				}
				if (l1 == -1)
				{
					l1 = 2;
				}
				for (int i2 = 0; i2 < 3; i2++)
				{
					stc[i][0][i2] = 200;
					if (j1 == i2)
					{
						stc[i][0][i2] += (int) (55D * Math.random());
					}
					if (l1 == i2)
					{
						stc[i][0][i2] += 55;
					}
					stc[i][0][i2] = (stc[i][0][i2] * 2 + csky[i2]) / 3;
					stc[i][1][i2] = (stc[i][0][i2] + csky[i2]) / 2;
				}
				twn[i] = 3;
			} else
			{
				twn[i]--;
			}
			int k1 = 0;
			if (bst[i])
			{
				k1 = 1;
			}
			graphics2d.setColor(new Color(stc[i][1][0], stc[i][1][1], stc[i][1][2]));
			graphics2d.fillRect(j - 1, l, 3 + k1, 1 + k1);
			graphics2d.fillRect(j, l - 1, 1 + k1, 3 + k1);
			graphics2d.setColor(new Color(stc[i][0][0], stc[i][0][1], stc[i][0][2]));
			graphics2d.fillRect(j, l, 1 + k1, 1 + k1);
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
	
	void rot(double ai[], double ai1[], double k)
	{
		if (k != 0)
		{
			for (int i1 = 0; i1 < 3; i1++)
			{
				double j1 = ai[i1];
				double k1 = ai1[i1];
				ai[i1] = (double) 0 + (j1 - (double) 0) * Math.cos(k * 0.0174532925199432957D) - (k1 - (double) 0) * Math.sin(k * 0.0174532925199432957D);
				ai1[i1] = (double) 0 + (j1 - (double) 0) * Math.sin(k * 0.0174532925199432957D) + (k1 - (double) 0) * Math.cos(k * 0.0174532925199432957D);
			}
		}
	}
	
	private int xs(long i, long j)
	{
		if (j < cz)
		{
			j = cz;
		}
		return (int)((j - focus_point) * (cx - i) / j + i);
	}
	
	void addsp(int i, int j, int k)
	{
		if (nsp != 7)
		{
			spx[nsp] = i;
			spz[nsp] = j;
			sprad[nsp] = k;
			nsp++;
		}
	}
	
	public Medium()
	{
		focus_point = 400;
		ground = 250;
		skyline = -300;
		fogd = 7;
		loadnew = false;
		lightson = false;
		lightn = -1;
		lilo = 217;
		lton = false;
		noelec = 0;
		trk = 0;
		crs = false;
		cx = 400;
		cy = 225;
		cz = 50;
		xz = 0;
		zy = 0;
		x = 0;
		y = 0;
		z = 0;
		iw = 0;
		ih = 0;
		w = 800;
		h = 450;
		nsp = 0;
		spx = new int[7];
		spz = new int[7];
		sprad = new int[7];
		adv = 500;
		lastmaf = 0;
		checkpoint = -1;
		lastcheck = false;
		elecr = 0.0F;
		cpflik = false;
		nochekflk = false;
		cntrn = 0;
		trn = 0;
		hit = 45000;
		ogpx = null;
		ogpz = null;
		pvr = null;
		cgpx = null;
		cgpz = null;
		pmx = null;
		pcv = null;
		sgpx = 0;
		sgpz = 0;
		nrw = 0;
		ncl = 0;
		noc = 0;
		clx = null;
		clz = null;
		cmx = null;
		clax = null;
		clay = null;
		claz = null;
		clc = null;
		nmt = 0;
		mrd = null;
		nmv = null;
		mtx = null;
		mty = null;
		mtz = null;
		mtc = null;
		nst = 0;
		stx = null;
		stz = null;
		stc = null;
		bst = null;
		twn = null;
		resdown = 0;
		xy = 0;
	}
	
	int focus_point;
	int ground;
	private final int skyline;
	final int[] fade = { 3000, 4500, 6000, 7500, 9000, 10500, 12000, 13500, 15000, 16500, 18000, 19500, 21000, 22500, 24000, 25500 };
	final int[] cldd = { 210, 210, 210, 1, -1000 };
	final int[] clds = { 210, 210, 210 };
	final int[] csky = { 170, 220, 255 };
	final int[] cgrnd = { 205, 200, 200 };
	final int[] cpol = { 215, 210, 210 };
	final int[] crgrnd = { 205, 200, 200 };
	final int[] cfade = { 255, 220, 220 };
	final int[] snap = { 0, 0, 0 };
	int fogd;
	private final boolean loadnew;
	boolean lightson;
	private int lightn;
	private int lilo;
	private final boolean lton;
	int noelec;
	final int trk;
	final boolean crs;
	int cx;
	int cy;
	final int cz;
	float xz;
	float zy;
	int x;
	int y;
	int z;
	final int iw;
	final int ih;
	int w;
	int h;
	int nsp;
	final int[] spx;
	final int[] spz;
	final int[] sprad;
	final int adv;
	int lastmaf;
	int checkpoint;
	boolean lastcheck;
	float elecr;
	boolean cpflik;
	boolean nochekflk;
	private int cntrn;
	private final boolean[] diup = { false, false, false };
	private final int[] rand = { 0, 0, 0 };
	private int trn;
	final int hit;
	int ogpx[][];
	int ogpz[][];
	float pvr[][];
	int cgpx[];
	int cgpz[];
	int pmx[];
	float pcv[];
	int sgpx;
	int sgpz;
	int nrw;
	int ncl;
	int noc;
	int clx[];
	int clz[];
	int cmx[];
	int clax[][][];
	int clay[][][];
	int claz[][][];
	int clc[][][][];
	int nmt;
	int mrd[];
	int nmv[];
	int mtx[][];
	int mty[][];
	int mtz[][];
	int mtc[][][];
	int nst;
	int stx[];
	int stz[];
	int stc[][][];
	boolean bst[];
	int twn[];
	final int resdown;
	float xy;
}
