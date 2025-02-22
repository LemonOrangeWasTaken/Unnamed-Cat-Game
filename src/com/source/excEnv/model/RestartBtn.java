package com.source.excEnv.model;

import java.awt.Color;
import java.awt.Graphics2D;

import com.source.excEnv.main.Resource;

public class RestartBtn extends Button{

	public RestartBtn(float x, float y, float w, float h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	public RestartBtn(float x, float y, float w, float h, boolean b) {
		super(x, y, w, h, b);
		// TODO Auto-generated constructor stub
	}
	
	public void render(Graphics2D g2D) {
		if(super.activated) { // only render when activated
			if(!super.pressed) {
//			render state 1
				g2D.drawImage(Resource.restartNorm, (int)x, (int) y, null);
			} else {
//			render state 2
				g2D.drawImage(Resource.restartPressed, (int)x, (int) y, null);
			}
		}
	}
}
