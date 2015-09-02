package com.tincent.demo.service;

interface IMusicService {
	void open(int position);
	
	void start();
	
	void pause();
	
	void stop();
	
	void close();
	
	void seekTo(int msec);
	
	int getMediaStyle();
	
	int getSelectedPosition();//position in list
	
	int getCurrentPosition();
	
	int getDuration();
	
	int getState();
	
	boolean isPlaying();
	
	String getName();
	
	String getFrom();
	
	void setMusicTypeId(String mtid);
	
	String getMusicTypeId();
	
	void notifyOngoing();
	
	void notifyOutgoing();
	
	void setMediaStyle(int style);
}