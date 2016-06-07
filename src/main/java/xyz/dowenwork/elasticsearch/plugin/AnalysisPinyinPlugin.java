package xyz.dowenwork.elasticsearch.plugin;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;
import xyz.dowenwork.elasticsearch.index.analysis.PinyinAnalysisBinderProcessor;
import xyz.dowenwork.elasticsearch.indices.analysis.PinyinAnalysisModule;

import java.util.Collection;
import java.util.Collections;

/**
 * <p>create at 16-6-6</p>
 *
 * @author liufl
 * @since 2.3.3.0
 */
@SuppressWarnings("unused")
public class AnalysisPinyinPlugin extends Plugin {
    @Override
    public String name() {
        return "analysis-pinyin";
    }

    @Override
    public String description() {
        return "拼音转换";
    }

    @Override
    public Collection<Module> nodeModules() {
        return Collections.<Module>singletonList(new PinyinAnalysisModule());
    }

    /**
     * Automatically called with the analysis module.
     */
    public void onModule(AnalysisModule module) {
        module.addProcessor(new PinyinAnalysisBinderProcessor());
    }
}
