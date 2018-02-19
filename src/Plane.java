// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name: Plane.java
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

class Plane implements Serializable
{
	private static final long serialVersionUID = 1L;
	Plane(Medium medium, Trackers trackers, int ai[], int ai1[], int ai2[], int i, int ai3[], int j, int k, int l, int i1, int j1, int k1, int l1, int i2, boolean flag, int j2, boolean flag1)
	{
		c = new int[3];
		oc = new int[3];
		hsb = new float[3];
		glass = 0;
		gr = 0;
		fs = 0;
		disline = 7;
		road = false;
		solo = false;
		light = 0;
		master = 0;
		wx = 0;
		wz = 0;
		wy = 0;
		deltaf = 1.0F;
		projf = 1.0F;
		av = 0;
		bfase = 0;
		nocol = false;
		chip = 0;
		ctmag = 0.0F;
		cxz = 0;
		cxy = 0;
		czy = 0;
		cox = new int[3];
		coz = new int[3];
		coy = new int[3];
		dx = 0;
		dy = 0;
		dz = 0;
		vx = 0;
		vy = 0;
		vz = 0;
		embos = 0;
		typ = 0;
		pa = 0;
		pb = 0;
		flx = 0;
		colnum = 0;
		m = medium;
		t = trackers;
		n = i;
		ox = new int[n];
		oz = new int[n];
		oy = new int[n];
		for (int k2 = 0; k2 < n; k2++)
		{
			ox[k2] = ai[k2];
			oy[k2] = ai2[k2];
			oz[k2] = ai1[k2];
		}
		System.arraycopy(ai3, 0, oc, 0, 3);
		if (k == -15)
		{
			if (ai3[0] == 211)
			{
				int i3 = (int) (Math.random() * 40D - 20D);
				int k4 = (int) (Math.random() * 40D - 20D);
				for (int i5 = 0; i5 < n; i5++)
				{
					ox[i5] += i3;
					oz[i5] += k4;
				}
			}
			int j3 = (int) (185D + Math.random() * 20D);
			ai3[0] = (217 + j3) / 2;
			if (ai3[0] == 211)
			{
				ai3[0] = 210;
			}
			ai3[1] = (189 + j3) / 2;
			ai3[2] = (132 + j3) / 2;
			for (int l4 = 0; l4 < n; l4++)
			{
				if (Math.random() > Math.random())
				{
					ox[l4] += (int) (8D * Math.random() - 4D);
				}
				if (Math.random() > Math.random())
				{
					oy[l4] += (int) (8D * Math.random() - 4D);
				}
				if (Math.random() > Math.random())
				{
					oz[l4] += (int) (8D * Math.random() - 4D);
				}
			}
		}
		if (ai3[0] == ai3[1] && ai3[1] == ai3[2])
		{
			nocol = true;
		}
		if (j == 0)
		{
			for (int k3 = 0; k3 < 3; k3++)
			{
				c[k3] = (int) (ai3[k3] + ai3[k3] * (m.snap[k3] / 100F));
				if (c[k3] > 255)
				{
					c[k3] = 255;
				}
				if (c[k3] < 0)
				{
					c[k3] = 0;
				}
			}
		}
		if (j == 1)
		{
			for (int l3 = 0; l3 < 3; l3++)
			{
				c[l3] = (m.csky[l3] * m.fade[0] * 2 + m.cfade[l3] * 3000) / (m.fade[0] * 2 + 3000);
			}
		}
		if (j == 2)
		{
			for (int i4 = 0; i4 < 3; i4++)
			{
				c[i4] = (int) (m.crgrnd[i4] * 0.925F);
			}
		}
		if (j == 3)
		{
			System.arraycopy(ai3, 0, c, 0, 3);
		}
		disline = l1;
		bfase = i2;
		glass = j;
		Color.RGBtoHSB(c[0], c[1], c[2], hsb);
		if (j == 3 && m.trk != 2)
		{
			hsb[1] += 0.05F;
			if (hsb[1] > 1.0F)
			{
				hsb[1] = 1.0F;
			}
		}
		if (!nocol && glass != 1)
		{
			if (bfase > 20 && hsb[1] > 0.25D)
			{
				hsb[1] = 0.25F;
			}
			if (bfase > 25 && hsb[2] > 0.69999999999999996D)
			{
				hsb[2] = 0.7F;
			}
			if (bfase > 30 && hsb[1] > 0.14999999999999999D)
			{
				hsb[1] = 0.15F;
			}
			if (bfase > 35 && hsb[2] > 0.59999999999999998D)
			{
				hsb[2] = 0.6F;
			}
			if (bfase > 40)
			{
				hsb[0] = 0.075F;
			}
			if (bfase > 50 && hsb[2] > 0.5D)
			{
				hsb[2] = 0.5F;
			}
			if (bfase > 60)
			{
				hsb[0] = 0.05F;
			}
		}
		road = flag;
		light = j2;
		solo = flag1;
		gr = k;
		fs = l;
		wx = i1;
		wy = j1;
		wz = k1;
		deltafntyp();
		fmod = 1.0F;
	}

	private void deltafntyp()
	{
		int i = Math.abs(ox[2] - ox[1]);
		int j = Math.abs(oy[2] - oy[1]);
		int k = Math.abs(oz[2] - oz[1]);
		if (j <= i && j <= k)
		{
			typ = 2;
		}
		if (i <= j && i <= k)
		{
			typ = 1;
		}
		if (k <= i && k <= j)
		{
			typ = 3;
		}
		deltaf = 1.0F;
		for (int l = 0; l < 3; l++)
		{
			for (int i1 = 0; i1 < 3; i1++)
			{
				if (i1 != l)
				{
					deltaf *= (float)(Math.sqrt((long)(ox[i1] - ox[l]) * (long)(ox[i1] - ox[l]) + (long)(oy[i1] - oy[l]) * (long)(oy[i1] - oy[l]) + (long)(oz[i1] - oz[l]) * (long)(oz[i1] - oz[l])) / 100D);
				}
			}
		}
		deltaf = deltaf / 3F;
	}

	void loadprojf()
	{
		projf = 1.0F;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (j != i)
				{
					projf *= (float)(Math.sqrt((long)(ox[i] - ox[j]) * (long)(ox[i] - ox[j]) + (long)(oz[i] - oz[j]) * (long)(oz[i] - oz[j])) / 100D);
				}
			}
		}
		projf = projf / 3F;
	}

	void d(Graphics2D graphics2d, int i, int j, int k, int l, int i1, int j1, float sin_c_xy, float cos_c_xy, float sin_c_zy, float cos_c_zy, float sin_c_xz, float cos_c_xz, int k1, int l1, boolean flag, int i2)
	{
		if (master == 1)
		{
			if (av > 1500 && !m.crs)
			{
				n = 12;
			} else
			{
				n = 20;
			}
		}
		int ai[] = new int[n];
		int ai1[] = new int[n];
		int ai2[] = new int[n];
		if (embos == 0)
		{
			if (m.lastmaf == 1 && (gr == -11 || gr == -12 || gr == -13))
			{
				for (int k2 = 0; k2 < n; k2++)
				{
					ai[k2] = -ox[k2] + i;
					ai2[k2] = oy[k2] + j;
					ai1[k2] = -oz[k2] + k;
				}
			} else
			{
				for (int j2 = 0; j2 < n; j2++)
				{
					ai[j2] = ox[j2] + i;
					ai2[j2] = oy[j2] + j;
					ai1[j2] = oz[j2] + k;
				}
			}
		} else
		{
			if (embos <= 11 && m.random() > 0.5D && glass != 1)
			{
				for (int l2 = 0; l2 < n; l2++)
				{
					ai[l2] = (int) (ox[l2] + i + (15F - m.random() * 30F));
					ai2[l2] = (int) (oy[l2] + j + (15F - m.random() * 30F));
					ai1[l2] = (int) (oz[l2] + k + (15F - m.random() * 30F));
				}
				rot(ai, ai2, i, j, sin_c_xy, cos_c_xy, n);
				rot(ai2, ai1, j, k, sin_c_zy, cos_c_zy, n);
				rot(ai, ai1, i, k, sin_c_xz, cos_c_xz, n);
				rot(ai, ai1, m.cx, m.cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, n);
				rot(ai2, ai1, m.cy, m.cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, n);
				rot(ai, ai2, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, n);
				int ai3[] = new int[n];
				int ai5[] = new int[n];
				for (int i4 = 0; i4 < n; i4++)
				{
					ai3[i4] = xs(ai[i4], ai1[i4]);
					ai5[i4] = ys(ai2[i4], ai1[i4]);
				}
				graphics2d.setColor(new Color(230, 230, 230));
				graphics2d.fillPolygon(ai3, ai5, n);
			}
			if (embos >= 16)
			{
				byte byte0 = 1;
				byte byte1 = 1;
				int j4;
				for (j4 = Math.abs(j1); j4 > 270; j4 -= 360)
				{
                }
				j4 = Math.abs(j4);
				if (j4 > 90)
				{
					byte0 = -1;
				}
				int j5;
				for (j5 = Math.abs(i1); j5 > 270; j5 -= 360)
				{
                }
				j5 = Math.abs(j5);
				if (j5 > 90)
				{
					byte1 = -1;
				}
				int ai12[] = new int[3];
				int ai13[] = new int[3];
				ai[0] = ox[pa] + i;
				ai2[0] = oy[pa] + j;
				ai1[0] = oz[pa] + k;
				ai[1] = ox[pb] + i;
				ai2[1] = oy[pb] + j;
				for (ai1[1] = oz[pb] + k; Math.abs(ai[0] - ai[1]) > 100;)
				{
					if (ai[1] > ai[0])
					{
						ai[1] -= 30;
					} else
					{
						ai[1] += 30;
					}
				}
				while (Math.abs(ai1[0] - ai1[1]) > 100)
				{
					if (ai1[1] > ai1[0])
					{
						ai1[1] -= 30;
					} else
					{
						ai1[1] += 30;
					}
				}
				int k7 = (int) (Math.abs(ai[0] - ai[1]) / 3 * (0.5D - m.random()));
				int j8 = (int) (Math.abs(ai1[0] - ai1[1]) / 3 * (0.5D - m.random()));
				ai[2] = (ai[0] + ai[1]) / 2 + k7;
				ai1[2] = (ai1[0] + ai1[1]) / 2 + j8;
				int k8 = (int) ((Math.abs(ai[0] - ai[1]) + Math.abs(ai1[0] - ai1[1])) / 1.5D * (m.random() / 2.0F + 0.5D));
				ai2[2] = (ai2[0] + ai2[1]) / 2 - byte0 * byte1 * k8;
				rot(ai, ai2, i, j, sin_c_xy, cos_c_xy, 3);
				rot(ai2, ai1, j, k, sin_c_zy, cos_c_zy, 3);
				rot(ai, ai1, i, k, sin_c_xz, cos_c_xz, 3);
				rot(ai, ai1, m.cx, m.cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, 3);
				rot(ai2, ai1, m.cy, m.cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, 3);
				rot(ai, ai2, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 3);
				for (int i9 = 0; i9 < 3; i9++)
				{
					ai12[i9] = xs(ai[i9], ai1[i9]);
					ai13[i9] = ys(ai2[i9], ai1[i9]);
				}
				int j9 = (int) (255F + 255F * (m.snap[0] / 400F));
				if (j9 > 255)
				{
					j9 = 255;
				}
				if (j9 < 0)
				{
					j9 = 0;
				}
				int l9 = (int) (169F + 169F * (m.snap[1] / 300F));
				if (l9 > 255)
				{
					l9 = 255;
				}
				if (l9 < 0)
				{
					l9 = 0;
				}
				int j10 = (int) (89F + 89F * (m.snap[2] / 200F));
				if (j10 > 255)
				{
					j10 = 255;
				}
				if (j10 < 0)
				{
					j10 = 0;
				}
				graphics2d.setColor(new Color(j9, l9, j10));
				graphics2d.fillPolygon(ai12, ai13, 3);
				ai[0] = ox[pa] + i;
				ai2[0] = oy[pa] + j;
				ai1[0] = oz[pa] + k;
				ai[1] = ox[pb] + i;
				ai2[1] = oy[pb] + j;
				for (ai1[1] = oz[pb] + k; Math.abs(ai[0] - ai[1]) > 100;)
				{
					if (ai[1] > ai[0])
					{
						ai[1] -= 30;
					} else
					{
						ai[1] += 30;
					}
				}
				while (Math.abs(ai1[0] - ai1[1]) > 100)
				{
					if (ai1[1] > ai1[0])
					{
						ai1[1] -= 30;
					} else
					{
						ai1[1] += 30;
					}
				}
				ai[2] = (ai[0] + ai[1]) / 2 + k7;
				ai1[2] = (ai1[0] + ai1[1]) / 2 + j8;
				k8 = (int) (k8 * 0.80000000000000004D);
				ai2[2] = (ai2[0] + ai2[1]) / 2 - byte0 * byte1 * k8;
				rot(ai, ai2, i, j, sin_c_xy, cos_c_xy, 3);
				rot(ai2, ai1, j, k, sin_c_zy, cos_c_zy, 3);
				rot(ai, ai1, i, k, sin_c_xz, cos_c_xz, 3);
				rot(ai, ai1, m.cx, m.cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, 3);
				rot(ai2, ai1, m.cy, m.cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, 3);
				rot(ai, ai2, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, 3);
				for (int l10 = 0; l10 < 3; l10++)
				{
					ai12[l10] = xs(ai[l10], ai1[l10]);
					ai13[l10] = ys(ai2[l10], ai1[l10]);
				}
				j9 = (int) (255F + 255F * (m.snap[0] / 400F));
				if (j9 > 255)
				{
					j9 = 255;
				}
				if (j9 < 0)
				{
					j9 = 0;
				}
				l9 = (int) (207F + 207F * (m.snap[1] / 300F));
				if (l9 > 255)
				{
					l9 = 255;
				}
				if (l9 < 0)
				{
					l9 = 0;
				}
				j10 = (int) (136F + 136F * (m.snap[2] / 200F));
				if (j10 > 255)
				{
					j10 = 255;
				}
				if (j10 < 0)
				{
					j10 = 0;
				}
				graphics2d.setColor(new Color(j9, l9, j10));
				graphics2d.fillPolygon(ai12, ai13, 3);
			}
			for (int k3 = 0; k3 < n; k3++)
			{
				if (typ == 1)
				{
					ai[k3] = (int) (ox[k3] * fmod + i);
				} else
				{
					ai[k3] = ox[k3] + i;
				}
				if (typ == 2)
				{
					ai2[k3] = (int) (oy[k3] * fmod + j);
				} else
				{
					ai2[k3] = oy[k3] + j;
				}
				if (typ == 3)
				{
					ai1[k3] = (int) (oz[k3] * fmod + k);
				} else
				{
					ai1[k3] = oz[k3] + k;
				}
			}
		}
		if (wz != 0)
		{
			rot(ai2, ai1, wy + j, wz + k, l1, n);
		}
		if (wx != 0)
		{
			rot(ai, ai1, wx + i, wz + k, k1, n);
		}
		if (chip != 0)
		{
			if (bfase != -7)
			{
				if (chipc == 0)
				{
					graphics2d.setColor(new Color(c[0], c[1], c[2]).darker());
				}
				if (chipc == 1)
				{
					graphics2d.setColor(new Color(c[0], c[1], c[2]));
				}
				if (chipc == 2)
				{
					graphics2d.setColor(new Color(c[0], c[1], c[2]).brighter());
				}
			} else
			{
				graphics2d.setColor(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
			}
			graphics2d.fillPolygon(new int[]{chipx1, chipx2, chipx3}, new int[]{chipy1, chipy2, chipy3}, 3);
		}
		rot(ai, ai2, i, j, sin_c_xy, cos_c_xy, n);
		rot(ai2, ai1, j, k, sin_c_zy, cos_c_zy, n);
		rot(ai, ai1, i, k, sin_c_xz, cos_c_xz, n);
		if ((i1 != 0 || j1 != 0 || l != 0) && m.trk != 2)
		{
			projf = 1.0F;
			for (int j3 = 0; j3 < 3; j3++)
			{
				for (int l3 = 0; l3 < 3; l3++)
				{
					if (l3 != j3)
					{
						projf *= (float)(Math.sqrt((long)(ai[j3] - ai[l3]) * (long)(ai[j3] - ai[l3]) + (long)(ai1[j3] - ai1[l3]) * (long)(ai1[j3] - ai1[l3])) / 100D);
					}
				}
			}
			projf = projf / 3F;
		}
		rot(ai, ai1, m.cx, m.cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, n);
		boolean flag1 = false;
		int ai7[] = new int[n];
		int ai9[] = new int[n];
		int i5 = 500;
		for (int k5 = 0; k5 < n; k5++)
		{
			ai7[k5] = xs(ai[k5], ai1[k5]);
			ai9[k5] = ys(ai2[k5], ai1[k5]);
		}
		int l5 = 0;
		int k6 = 1;
		for (int l6 = 0; l6 < n; l6++)
		{
			for (int l7 = l6; l7 < n; l7++)
			{
				if (l6 != l7 && Math.abs(ai7[l6] - ai7[l7]) - Math.abs(ai9[l6] - ai9[l7]) < i5)
				{
					k6 = l6;
					l5 = l7;
					i5 = Math.abs(ai7[l6] - ai7[l7]) - Math.abs(ai9[l6] - ai9[l7]);
				}
			}
		}
		if (ai9[l5] < ai9[k6])
		{
			int i7 = l5;
			l5 = k6;
			k6 = i7;
		}
		if (spy(ai[l5], ai1[l5]) > spy(ai[k6], ai1[k6]))
		{
			flag1 = true;
			int j7 = 0;
			for (int i8 = 0; i8 < n; i8++)
			{
				if (ai1[i8] < 50 && ai2[i8] > m.cy)
				{
					flag1 = false;
					continue;
				}
				if (ai2[i8] == ai2[0])
				{
					j7++;
				}
			}
			if (j7 == n && ai2[0] > m.cy)
			{
				flag1 = false;
			}
		}
		rot(ai2, ai1, m.cy, m.cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, n);
		rot(ai, ai2, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, n);
		boolean flag2 = true;
		int ai14[] = new int[n];
		int ai15[] = new int[n];
		int l8 = 0;
		int k9 = 0;
		int i10 = 0;
		int k10 = 0;
		int i11 = 0;
		for (int j11 = 0; j11 < n; j11++)
		{
			ai14[j11] = xs(ai[j11], ai1[j11]);
			ai15[j11] = ys(ai2[j11], ai1[j11]);
			if (ai15[j11] < m.ih || ai1[j11] < 10)
			{
				l8++;
			}
			if (ai15[j11] > m.h || ai1[j11] < 10)
			{
				k9++;
			}
			if (ai14[j11] < m.iw || ai1[j11] < 10)
			{
				i10++;
			}
			if (ai14[j11] > m.w || ai1[j11] < 10)
			{
				k10++;
			}
			if (ai1[j11] < 10)
			{
				i11++;
			}
		}
		if (i10 == n || l8 == n || k9 == n || k10 == n)
		{
			flag2 = false;
		}
		if ((m.trk == 1 || m.trk == 4) && (i10 != 0 || l8 != 0 || k9 != 0 || k10 != 0))
		{
			flag2 = false;
		}
		if (m.trk == 3 && i11 != 0)
		{
			flag2 = false;
		}
		if (i11 != 0)
		{
			flag = true;
		}
		if (flag2 && i2 != -1)
		{
			int k11 = 0;
			int i12 = 0;
			for (int k12 = 0; k12 < n; k12++)
			{
				for (int i14 = k12; i14 < n; i14++)
				{
					if (k12 == i14)
					{
						continue;
					}
					if (Math.abs(ai14[k12] - ai14[i14]) > k11)
					{
						k11 = Math.abs(ai14[k12] - ai14[i14]);
					}
					if (Math.abs(ai15[k12] - ai15[i14]) > i12)
					{
						i12 = Math.abs(ai15[k12] - ai15[i14]);
					}
				}
			}
			if (k11 == 0 || i12 == 0)
			{
				flag2 = false;
			} else if (k11 < 3 && i12 < 3 && (i2 / k11 > 15 && i2 / i12 > 15 || flag) && (!m.lightson || light == 0))
			{
				flag2 = false;
			}
		}
		if (flag2)
		{
			int l11 = 1;
			int j12 = gr;
			if (j12 < 0 && j12 >= -15)
			{
				j12 = 0;
			}
			if (gr == -11)
			{
				j12 = -90;
			}
			if (gr == -12)
			{
				j12 = -75;
			}
			if (gr == -14 || gr == -15)
			{
				j12 = -50;
			}
			if (glass == 2)
			{
				j12 = 200;
			}
			if (fs != 0)
			{
				byte byte2;
				byte byte3;
				if (Math.abs(ai15[0] - ai15[1]) > Math.abs(ai15[2] - ai15[1]))
				{
					byte2 = 0;
					byte3 = 2;
				} else
				{
					byte2 = 2;
					byte3 = 0;
					l11 *= -1;
				}
				if (ai15[1] > ai15[byte2])
				{
					l11 *= -1;
				}
				if (ai14[1] > ai14[byte3])
				{
					l11 *= -1;
				}
				if (fs != 22)
				{
					l11 *= fs;
					if (l11 == -1)
					{
						j12 += 40;
						l11 = -111;
					}
				}
			}
			if (m.lightson && light == 2)
			{
				j12 -= 40;
			}
			int l12 = ai2[0];
			int j14 = ai2[0];
			int k15 = ai[0];
			int l16 = ai[0];
			int l17 = ai1[0];
			int i18 = ai1[0];
			for (int j18 = 0; j18 < n; j18++)
			{
				if (ai2[j18] > l12)
				{
					l12 = ai2[j18];
				}
				if (ai2[j18] < j14)
				{
					j14 = ai2[j18];
				}
				if (ai[j18] > k15)
				{
					k15 = ai[j18];
				}
				if (ai[j18] < l16)
				{
					l16 = ai[j18];
				}
				if (ai1[j18] > l17)
				{
					l17 = ai1[j18];
				}
				if (ai1[j18] < i18)
				{
					i18 = ai1[j18];
				}
			}
			int k18 = (l12 + j14) / 2;
			int l18 = (k15 + l16) / 2;
			int i19 = (l17 + i18) / 2;
			av = Math.sqrt((double)(m.cy - k18) * (double)(m.cy - k18) + (double)(m.cx - l18) * (double)(m.cx - l18) + (double)i19 * (double)i19 + (double)j12 * (double)j12 * (double)j12);
			if (m.trk == 0 && (av > m.fade[disline] || av == 0))
			{
				flag2 = false;
			}
			if (l11 == -111 && av > 4500 && !road)
			{
				flag2 = false;
			}
			if (l11 == -111 && av > 1500)
			{
				flag = true;
			}
			if (av > 3000 && m.adv <= 900)
			{
				flag = true;
			}
			if (fs == 22 && av < 11200)
			{
				m.lastmaf = l11;
			}
			if (gr == -13 && (!m.lastcheck || i2 != -1))
			{
				flag2 = false;
			}
			if (master == 2 && av > 1500 && !m.crs)
			{
				flag2 = false;
			}
			if ((gr == -14 || gr == -15 || gr == -12) && (av > 11000 || flag1 || l11 == -111 || m.resdown == 2) && m.trk != 2 && m.trk != 3)
			{
				flag2 = false;
			}
			if (gr == -11 && av > 11000 && m.trk != 2 && m.trk != 3)
			{
				flag2 = false;
			}
			if (glass == 2 && (m.trk != 0 || av > 6700))
			{
				flag2 = false;
			}
			if (flx != 0 && m.random() > 0.29999999999999999D && flx != 77)
			{
				flag2 = false;
			}
		}
		if (flag2)
		{
			float f1 = (float) (projf / deltaf + 0.29999999999999999D);
			if (flag && !solo)
			{
				boolean flag3 = false;
				if (f1 > 1.0F)
				{
					if (f1 >= 1.27D)
					{
						flag3 = true;
					}
					f1 = 1.0F;
				}
				if (flag3)
				{
					f1 = (float) (f1 * 0.89000000000000001D);
				} else
				{
					f1 = (float) (f1 * 0.85999999999999999D);
				}
				if (f1 < 0.37D)
				{
					f1 = 0.37F;
				}
				if (gr == -9)
				{
					f1 = 0.7F;
				}
				if (gr == -4)
				{
					f1 = 0.74F;
				}
				if (gr != -7 && m.trk == 0 && flag1)
				{
					f1 = 0.32F;
				}
				if (gr == -8 || gr == -14 || gr == -15)
				{
					f1 = 1.0F;
				}
				if (gr == -11 || gr == -12)
				{
					f1 = 0.6F;
					if (i2 == -1)
					{
						if (m.cpflik || m.nochekflk && !m.lastcheck)
						{
							f1 = 1.0F;
						} else
						{
							f1 = 0.76F;
						}
					}
				}
				if (gr == -13 && i2 == -1)
				{
					if (m.cpflik)
					{
						f1 = 0.0F;
					} else
					{
						f1 = 0.76F;
					}
				}
				if (gr == -6)
				{
					f1 = 0.62F;
				}
				if (gr == -5)
				{
					f1 = 0.55F;
				}
			} else
			{
				if (f1 > 1.0F)
				{
					f1 = 1.0F;
				}
				if (f1 < 0.59999999999999998D || flag1)
				{
					f1 = 0.6F;
				}
			}
			Color color = Color.getHSBColor(hsb[0], hsb[1], hsb[2] * f1);
			if (m.trk == 1)
			{
				float af[] = new float[3];
				Color.RGBtoHSB(oc[0], oc[1], oc[2], af);
				af[0] = 0.15F;
				af[1] = 0.3F;
				color = Color.getHSBColor(af[0], af[1], af[2] * f1 + 0.0F);
			}
			if (m.trk == 3)
			{
				float af1[] = new float[3];
				Color.RGBtoHSB(oc[0], oc[1], oc[2], af1);
				af1[0] = 0.6F;
				af1[1] = 0.14F;
				color = Color.getHSBColor(af1[0], af1[1], af1[2] * f1 + 0.0F);
			}
			int i13 = color.getRed();
			int k14 = color.getGreen();
			int l15 = color.getBlue();
			if (m.lightson && (light != 0 || (gr == -11 || gr == -12) && i2 == -1))
			{
				i13 = oc[0];
				if (i13 > 255)
				{
					i13 = 255;
				}
				if (i13 < 0)
				{
					i13 = 0;
				}
				k14 = oc[1];
				if (k14 > 255)
				{
					k14 = 255;
				}
				if (k14 < 0)
				{
					k14 = 0;
				}
				l15 = oc[2];
				if (l15 > 255)
				{
					l15 = 255;
				}
				if (l15 < 0)
				{
					l15 = 0;
				}
			}
			if (m.trk == 0)
			{
				for (int i17 = 0; i17 < 16; i17++)
				{
					if (av > m.fade[i17])
					{
						i13 = (i13 * m.fogd + m.cfade[0]) / (m.fogd + 1);
						k14 = (k14 * m.fogd + m.cfade[1]) / (m.fogd + 1);
						l15 = (l15 * m.fogd + m.cfade[2]) / (m.fogd + 1);
					}
				}
			}
			graphics2d.setColor(new Color(i13, k14, l15));
			graphics2d.fillPolygon(ai14, ai15, n);
			if (m.trk != 0 && gr == -10)
			{
				flag = false;
			}
			if (!flag)
			{
				if (flx == 0)
				{
					if (!solo)
					{
						i13 = 0;
						k14 = 0;
						l15 = 0;
						if (m.lightson && light != 0)
						{
							i13 = oc[0] / 2;
							if (i13 > 255)
							{
								i13 = 255;
							}
							if (i13 < 0)
							{
								i13 = 0;
							}
							k14 = oc[1] / 2;
							if (k14 > 255)
							{
								k14 = 255;
							}
							if (k14 < 0)
							{
								k14 = 0;
							}
							l15 = oc[2] / 2;
							if (l15 > 255)
							{
								l15 = 255;
							}
							if (l15 < 0)
							{
								l15 = 0;
							}
						}
						graphics2d.setColor(new Color(i13, k14, l15));
						graphics2d.drawPolygon(ai14, ai15, n);
					}
				} else
				{
					if (flx == 2)
					{
						graphics2d.setColor(new Color(0, 0, 0));
						graphics2d.drawPolygon(ai14, ai15, n);
					}
					if (flx == 1)
					{
						i13 = 0;
						k14 = (int) (223F + 223F * (m.snap[1] / 100F));
						if (k14 > 255)
						{
							k14 = 255;
						}
						if (k14 < 0)
						{
							k14 = 0;
						}
						l15 = (int) (255F + 255F * (m.snap[2] / 100F));
						if (l15 > 255)
						{
							l15 = 255;
						}
						if (l15 < 0)
						{
							l15 = 0;
						}
						graphics2d.setColor(new Color(i13, k14, l15));
						graphics2d.drawPolygon(ai14, ai15, n);
						flx = 2;
					}
					if (flx == 3)
					{
						i13 = 0;
						k14 = (int) (255F + 255F * (m.snap[1] / 100F));
						if (k14 > 255)
						{
							k14 = 255;
						}
						if (k14 < 0)
						{
							k14 = 0;
						}
						l15 = (int) (223F + 223F * (m.snap[2] / 100F));
						if (l15 > 255)
						{
							l15 = 255;
						}
						if (l15 < 0)
						{
							l15 = 0;
						}
						graphics2d.setColor(new Color(i13, k14, l15));
						graphics2d.drawPolygon(ai14, ai15, n);
						flx = 2;
					}
					if (flx == 77)
					{
						graphics2d.setColor(new Color(16, 198, 255));
						graphics2d.drawPolygon(ai14, ai15, n);
						flx = 0;
					}
				}
			} else if (road && av <= 3000 && m.trk == 0 && m.fade[0] > 4000)
			{
				if ((i13 -= 10) < 0)
				{
					i13 = 0;
				}
				if ((k14 -= 10) < 0)
				{
					k14 = 0;
				}
				if ((l15 -= 10) < 0)
				{
					l15 = 0;
				}
				graphics2d.setColor(new Color(i13, k14, l15));
				graphics2d.drawPolygon(ai14, ai15, n);
			}
			if (gr == -10)
			{
				if (m.trk == 0)
				{
					int j13 = c[0];
					int l14 = c[1];
					int i16 = c[2];
					if (i2 == -1 && m.cpflik)
					{
						j13 = (int) (j13 * 1.6000000000000001D);
						if (j13 > 255)
						{
							j13 = 255;
						}
						l14 = (int) (l14 * 1.6000000000000001D);
						if (l14 > 255)
						{
							l14 = 255;
						}
						i16 = (int) (i16 * 1.6000000000000001D);
						if (i16 > 255)
						{
							i16 = 255;
						}
					}
					for (int j17 = 0; j17 < 16; j17++)
					{
						if (av > m.fade[j17])
						{
							j13 = (j13 * m.fogd + m.cfade[0]) / (m.fogd + 1);
							l14 = (l14 * m.fogd + m.cfade[1]) / (m.fogd + 1);
							i16 = (i16 * m.fogd + m.cfade[2]) / (m.fogd + 1);
						}
					}
					graphics2d.setColor(new Color(j13, l14, i16));
					graphics2d.drawPolygon(ai14, ai15, n);
				} else if (m.cpflik && m.hit == 5000)
				{
					int i15 = (int) (Math.random() * 115D);
					int k13 = i15 * 2 - 54;
					if (k13 < 0)
					{
						k13 = 0;
					}
					if (k13 > 255)
					{
						k13 = 255;
					}
					int j16 = 202 + i15 * 2;
					if (j16 < 0)
					{
						j16 = 0;
					}
					if (j16 > 255)
					{
						j16 = 255;
					}
					if ((i15 += 101) < 0)
					{
						i15 = 0;
					}
					if (i15 > 255)
					{
						i15 = 255;
					}
					graphics2d.setColor(new Color(k13, i15, j16));
					graphics2d.drawPolygon(ai14, ai15, n);
				}
			}
			if (gr == -18 && m.trk == 0)
			{
				int l13 = c[0];
				int j15 = c[1];
				int k16 = c[2];
				if (m.cpflik && m.elecr >= 0.0F)
				{
					l13 = (int) (25.5F * m.elecr);
					if (l13 > 255)
					{
						l13 = 255;
					}
					j15 = (int) (128F + 12.8F * m.elecr);
					if (j15 > 255)
					{
						j15 = 255;
					}
					k16 = 255;
				}
				for (int k17 = 0; k17 < 16; k17++)
				{
					if (av > m.fade[k17])
					{
						l13 = (l13 * m.fogd + m.cfade[0]) / (m.fogd + 1);
						j15 = (j15 * m.fogd + m.cfade[1]) / (m.fogd + 1);
						k16 = (k16 * m.fogd + m.cfade[2]) / (m.fogd + 1);
					}
				}
				graphics2d.setColor(new Color(l13, j15, k16));
				graphics2d.drawPolygon(ai14, ai15, n);
			}
		}
	}

	void s(Graphics2D graphics2d, int i, int j, int k, float sin_c_xy, float cos_c_xy, float sin_c_zy, float cos_c_zy, float sin_c_xz, float cos_c_xz, int k1)
	{
		int ai[] = new int[n];
		int ai1[] = new int[n];
		int ai2[] = new int[n];
		for (int l1 = 0; l1 < n; l1++)
		{
			ai[l1] = ox[l1] + i;
			ai2[l1] = oy[l1] + j;
			ai1[l1] = oz[l1] + k;
		}
		rot(ai, ai2, i, j, sin_c_xy, cos_c_xy, n);
		rot(ai2, ai1, j, k, sin_c_zy, cos_c_zy, n);
		rot(ai, ai1, i, k, sin_c_xz, cos_c_xz, n);
		int i2 = (int) (m.crgrnd[0] / 1.5D);
		int j2 = (int) (m.crgrnd[1] / 1.5D);
		int k2 = (int) (m.crgrnd[2] / 1.5D);
		for (int l2 = 0; l2 < n; l2++)
		{
			ai2[l2] = m.ground;
		}
		if (k1 == 0)
		{
			int i3 = 0;
			int j3 = 0;
			int k3 = 0;
			int l3 = 0;
			for (int l4 = 0; l4 < n; l4++)
			{
				int l5 = 0;
				int k6 = 0;
				int j7 = 0;
				int i8 = 0;
				for (int k8 = 0; k8 < n; k8++)
				{
					if (ai[l4] >= ai[k8])
					{
						l5++;
					}
					if (ai[l4] <= ai[k8])
					{
						k6++;
					}
					if (ai1[l4] >= ai1[k8])
					{
						j7++;
					}
					if (ai1[l4] <= ai1[k8])
					{
						i8++;
					}
				}
				if (l5 == n)
				{
					i3 = ai[l4];
				}
				if (k6 == n)
				{
					j3 = ai[l4];
				}
				if (j7 == n)
				{
					k3 = ai1[l4];
				}
				if (i8 == n)
				{
					l3 = ai1[l4];
				}
			}
			int i5 = (i3 + j3) / 2;
			int i6 = (k3 + l3) / 2;
			int l6 = (i5 - t.sx + m.x) / 3000;
			if (l6 > t.ncx)
			{
				l6 = t.ncx;
			}
			if (l6 < 0)
			{
				l6 = 0;
			}
			int k7 = (i6 - t.sz + m.z) / 3000;
			if (k7 > t.ncz)
			{
				k7 = t.ncz;
			}
			if (k7 < 0)
			{
				k7 = 0;
			}
			int j8 = t.sect[l6][k7].length - 1;
			do
			{
				if (j8 < 0)
				{
					break;
				}
				int l8 = t.sect[l6][k7][j8];
				int i9 = 0;
				if (Math.abs(t.zy[l8]) != 90 && Math.abs(t.xy[l8]) != 90 && t.rady[l8] != 801 && Math.abs(i5 - (t.x[l8] - m.x)) < t.radx[l8] && Math.abs(i6 - (t.z[l8] - m.z)) < t.radz[l8] && (!t.decor[l8] || m.resdown != 2))
				{
					i9++;
				}
				if (i9 != 0)
				{
					for (int j9 = 0; j9 < n; j9++)
					{
						ai2[j9] = t.y[l8] - m.y;
						if (t.zy[l8] != 0)
						{
							ai2[j9] += (ai1[j9] - (t.z[l8] - m.z - t.radz[l8])) * (float)Math.sin(0.0174532925199432957 * t.zy[l8]) / (float)Math.sin(0.0174532925199432957 * (90 - t.zy[l8])) - t.radz[l8] * (float)Math.sin(0.0174532925199432957 * t.zy[l8]) / (float)Math.sin(0.0174532925199432957 * (90 - t.zy[l8]));
						}
						if (t.xy[l8] != 0)
						{
							ai2[j9] += (ai[j9] - (t.x[l8] - m.x - t.radx[l8])) * (float)Math.sin(0.0174532925199432957 * t.xy[l8]) / (float)Math.sin(0.0174532925199432957 * (90 - t.xy[l8])) - t.radx[l8] * (float)Math.sin(0.0174532925199432957 * t.xy[l8]) / (float)Math.sin(0.0174532925199432957 * (90 - t.xy[l8]));
						}
					}
					i2 = (int) (t.c[l8][0] / 1.5D);
					j2 = (int) (t.c[l8][1] / 1.5D);
					k2 = (int) (t.c[l8][2] / 1.5D);
					break;
				}
				j8--;
			} while (true);
		}
		boolean flag = true;
		int ai3[] = new int[n];
		int ai4[] = new int[n];
		if (k1 == 2)
		{
			i2 = 87;
			j2 = 85;
			k2 = 57;
		} else
		{
			for (int i4 = 0; i4 < m.nsp; i4++)
			{
				for (int j5 = 0; j5 < n; j5++)
				{
					if (Math.abs(ai[j5] - m.spx[i4]) < m.sprad[i4] && Math.abs(ai1[j5] - m.spz[i4]) < m.sprad[i4])
					{
						flag = false;
					}
				}
			}
		}
		if (flag)
		{
			rot(ai, ai1, m.cx, m.cz, ReplayViewer.sin_m_xz, ReplayViewer.cos_m_xz, n);
			rot(ai2, ai1, m.cy, m.cz, ReplayViewer.sin_m_zy, ReplayViewer.cos_m_zy, n);
			rot(ai, ai2, m.cx, m.cy, ReplayViewer.sin_m_xy, ReplayViewer.cos_m_xy, n);
			int j4 = 0;
			int k5 = 0;
			int j6 = 0;
			int i7 = 0;
			for (int l7 = 0; l7 < n; l7++)
			{
				ai3[l7] = xs(ai[l7], ai1[l7]);
				ai4[l7] = ys(ai2[l7], ai1[l7]);
				if (ai4[l7] < m.ih || ai1[l7] < 10)
				{
					j4++;
				}
				if (ai4[l7] > m.h || ai1[l7] < 10)
				{
					k5++;
				}
				if (ai3[l7] < m.iw || ai1[l7] < 10)
				{
					j6++;
				}
				if (ai3[l7] > m.w || ai1[l7] < 10)
				{
					i7++;
				}
			}
			if (j6 == n || j4 == n || k5 == n || i7 == n)
			{
				flag = false;
			}
		}
		if (flag)
		{
			for (int k4 = 0; k4 < 16; k4++)
			{
				if (av > m.fade[k4])
				{
					i2 = (i2 * m.fogd + m.cfade[0]) / (m.fogd + 1);
					j2 = (j2 * m.fogd + m.cfade[1]) / (m.fogd + 1);
					k2 = (k2 * m.fogd + m.cfade[2]) / (m.fogd + 1);
				}
			}
			graphics2d.setColor(new Color(i2, j2, k2));
			graphics2d.fillPolygon(ai3, ai4, n);
		}
	}

	int xs(long i, long j)
	{
		if (j < m.cz)
		{
			j = m.cz;
		}
		return (int)((j - m.focus_point) * (m.cx - i) / j + i);
	}

	int ys(long i, long j)
	{
		if (j < m.cz)
		{
			j = m.cz;
		}
		return (int)((j - m.focus_point) * (m.cy - i) / j + i);
	}

	void rot(int ai[], int ai1[], int i, int j, float k, int l)
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

	private int spy(long i, long j)
	{
		return (int)Math.sqrt((i - m.cx) * (i - m.cx) + j * j);
	}

	Medium m;
	Trackers t;
	final int[] ox;
	final int[] oy;
	final int[] oz;
	int n;
	final int[] c;
	final int[] oc;
	final float[] hsb;
	int glass;
	int gr;
	int fs;
	private int disline;
	boolean road;
	boolean solo;
	int light;
	int master;
	int wx;
	int wz;
	int wy;
	private float deltaf;
	private float projf;
	transient double av;
	int bfase;
	boolean nocol;
	int chip;
	float ctmag;
	int cxz;
	int cxy;
	int czy;
	final int[] cox;
	final int[] coz;
	final int[] coy;
	int dx;
	int dy;
	int dz;
	int vx;
	int vy;
	int vz;
	int embos;
	private int typ;
	int pa;
	int pb;
	int flx;
	int colnum;
	float fmod;
	
	int chipx1 = 0;
	int chipx2 = 0;
	int chipx3 = 0;
	
	int chipy1 = 0;
	int chipy2 = 0;
	int chipy3 = 0;
	
	int chipc = 0;
}
