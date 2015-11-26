package com.webmd.monitoring.service

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.UnknownHostException
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.util.Random

import org.springframework.stereotype.Component

/**
 * Daemon for recording metrics
 */
public interface StatsdClient {

	public boolean timing(String key, int value)

	public boolean timing(String key, int value, double sampleRate)

	public boolean decrement(String key)

	public boolean decrement(String key, int magnitude)

	public boolean decrement(String key, int magnitude, double sampleRate)

	public boolean decrement(String... keys)

	public boolean decrement(int magnitude, String... keys)

	public boolean decrement(int magnitude, double sampleRate, String... keys)

	public boolean increment(String key) 

	public boolean increment(String key, int magnitude) 

	public boolean increment(String key, int magnitude, double sampleRate) 

	public boolean increment(int magnitude, double sampleRate, String... keys) 

	boolean send(String stat, double sampleRate)

	boolean send(double sampleRate, String... stats)

	boolean doSend(final String stat) 
}

