package com.alecuba16.cdap;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.plugin.PluginConfig;
import io.cdap.cdap.etl.api.streaming.Windower;

/**
 * Windower Plugin
 * A Windower plugin is used in real-time data pipelines to create sliding windows over the data. It does this by combining multiple micro batches into larger batches.
 *
 * A window is defined by its size and its slide interval. Both are defined in seconds and must be multiples of the batchInterval of the pipeline. The size defines how much data is contained in the window. The slide interval defines have often a window is created.
 *
 * For example, consider a pipeline with a batchInterval of 10 seconds. The pipeline uses a windower that has a size of 60 and a slide interval of 30. The input into the windower will be micro batches containing 10 seconds of data. Every 30 seconds, the windower will output a batch of data containing the past 60 seconds of data, meaning the previous six micro batches that it received as input.
 *
 * This also means that each window output will overlap (repeat) some of the data from the previous window. This is useful in calculating aggregates, such as how many "404" responses did a website send out in the past ten seconds, past minute, past five minutes.
 *
 * In order to implement a Windower Plugin, you extend the Windower class.
 *
 * Methods
 * configurePipeline(): Used to perform any validation on the application configuration that is required by this plugin or to create any datasets if the fieldName for a dataset is not a macro.
 * getWidth(): Return the width in seconds of windows created by this plugin. Must be a multiple of the batchInterval of the pipeline.
 * getSlideInterval(): Get the slide interval in seconds of windows created by this plugin. Must be a multiple of the batchInterval of the pipeline.
 */

@Plugin(type = Windower.PLUGIN_TYPE)
@Name("Window")
@Description("Creates a sliding window over the data")
public class Window extends Windower {
    private final Conf conf;

    /**
     * Config for window plugin.
     */
    public static class Conf extends PluginConfig {
        long width;
        long slideInterval;
    }

    public Window(Conf conf) {
        this.conf = conf;
    }

    @Override
    public long getWidth() {
        return conf.width;
    }

    @Override
    public long getSlideInterval() {
        return conf.slideInterval;
    }
}
