package xyz.dowenwork.elasticsearch.plugin;

import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import xyz.dowenwork.elasticsearch.index.analysis.PinyinTokenFilterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>create at 16-6-6</p>
 *
 * @author liufl
 * @since 2.3.3.0
 */
@SuppressWarnings("unused")
public class AnalysisPinyinPlugin extends Plugin implements AnalysisPlugin {
    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> getTokenFilters() {
        Map<String, AnalysisModule.AnalysisProvider<TokenFilterFactory>> factories = new HashMap<>();

        factories.put("pinyin", (indexSettings, environment, name, settings) -> new PinyinTokenFilterFactory(indexSettings, name, settings));

        return factories;
    }
}
