import java.io.Serializable;

// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name: Trackers.java
class Trackers implements Serializable
{
	private static final long serialVersionUID = 1L;
	public Trackers()
	{
		x = new int[6700];
		y = new int[6700];
		z = new int[6700];
		xy = new int[6700];
		zy = new int[6700];
		skd = new int[6700];
		dam = new int[6700];
		notwall = new boolean[6700];
		decor = new boolean[6700];
		c = new int[6700][3];
		radx = new int[6700];
		radz = new int[6700];
		rady = new int[6700];
		nt = 0;
		sx = 0;
		sz = 0;
		ncx = 0;
		ncz = 0;
		sect = null;
	}

	final int[] x;
	final int[] y;
	final int[] z;
	final int[] xy;
	final int[] zy;
	final int[] skd;
	final int[] dam;
	final boolean[] notwall;
	final boolean[] decor;
	final int[][] c;
	final int[] radx;
	final int[] radz;
	final int[] rady;
	int nt;
	final int sx;
	final int sz;
	final int ncx;
	final int ncz;
	final int[][][] sect;
}
