package com.peafunk.monitoring.service

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
 * Daemon for recording metrics in StatsD
 * Available metrics: Increment, Decrement, Time
 */
public class StatsdClientImpl implements StatsdClient{
	private static Random RNG = new Random()
	private static Log logger = LogFactory.getLog(StatsdClient.class)

	private InetSocketAddress _address
	private DatagramChannel _channel

	public StatsdClientImpl(String host, int port) throws UnknownHostException, IOException {
		this(InetAddress.getByName(host), port)
	}

	public StatsdClient(InetAddress host, int port) throws IOException {
		_address = new InetSocketAddress(host, port)
		_channel = DatagramChannel.open()
	}

	public boolean timing(String key, int value) {
		return timing(key, value, 1.0)
	}

	public boolean timing(String key, int value, double sampleRate) {
		return send(sampleRate, String.format("%s:%d|ms", key, value))
	}

	public boolean decrement(String key) {
		return increment(key, -1, 1.0)
	}

	public boolean decrement(String key, int magnitude) {
		return decrement(key, magnitude, 1.0)
	}

	public boolean decrement(String key, int magnitude, double sampleRate) {
		magnitude = magnitude < 0 ? magnitude : -magnitude
		return increment(key, magnitude, sampleRate)
	}

	public boolean decrement(String... keys) {
		return increment(-1, 1.0, keys)
	}

	public boolean decrement(int magnitude, String... keys) {
		magnitude = magnitude < 0 ? magnitude : -magnitude;
		return increment(magnitude, 1.0, keys)
	}

	public boolean decrement(int magnitude, double sampleRate, String... keys) {
		magnitude = magnitude < 0 ? magnitude : -magnitude
		return increment(magnitude, sampleRate, keys)
	}

	public boolean increment(String key) {
		return increment(key, 1, 1.0)
	}

	public boolean increment(String key, int magnitude) {
		return increment(key, magnitude, 1.0)
	}

	public boolean increment(String key, int magnitude, double sampleRate) {
		String stat = String.format("%s:%s|c", key, magnitude)
		return send(stat, sampleRate)
	}

	public boolean increment(int magnitude, double sampleRate, String... keys) {
		String[] stats = new String[keys.length]
		for (int i = 0; i < keys.length; i++) {
			stats[i] = String.format("%s:%s|c", keys[i], magnitude)
		}
		return send(sampleRate, stats)
	}

	boolean send(String stat, double sampleRate) {
		return send(sampleRate, stat)
	}

	boolean send(double sampleRate, String... stats) {

		boolean retval = false // didn't send anything
		if (sampleRate < 1.0) {
			for (String stat : stats) {
				if (RNG.nextDouble() <= sampleRate) {
					stat = String.format("%s|@%f", stat, sampleRate)
					if (doSend(stat)) {
						retval = true
					}
				}
			}
		} else {
			for (String stat : stats) {
				if (doSend(stat)) {
					retval = true
				}
			}
		}
		return retval
	}

	boolean doSend(final String stat) {
		try {
			final byte[] data = stat.getBytes("utf-8")
			final ByteBuffer buff = ByteBuffer.wrap(data)
			final int nbSentBytes = _channel.send(buff, _address)

			if (data.length == nbSentBytes) {
				return true
			} else {
				logger.error(String.format("Could not send entirely stat %s to host %s:%d. Only sent %i bytes out of %i bytes", stat, _address.getHostName(), _address.getPort(), nbSentBytes, data.length))
				return false
			}

		} catch (IOException e) {
			logger.error(String.format("Could not send stat %s to host %s:%d", stat, _address.getHostName(),_address.getPort()), e)
			return false
		}
	}
}

