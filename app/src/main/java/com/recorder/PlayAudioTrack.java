package com.recorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.util.Log;

public class PlayAudioTrack {	
	//AudioTrack����ʧ�棬����mediaplayer
	//����QCMediaPlayer mediaplayer NOT present���󣬵�����������
    public static void PlayAudioTrack(String filePath)
                    throws IOException {
    	  MediaPlayer player = new MediaPlayer();
           player.setDataSource(filePath);
           player.prepare();
           player.start();
           //������Դ��֪����ͷ�
    }
}
