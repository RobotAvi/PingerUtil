package com.blogspot.positiveguru.domain;

import com.blogspot.positiveguru.reporting.Report;

/**
 * Created by Avenir Voronov on 7/18/2017.
 */
public interface PingClient {

    Report execute( Report report);
}
