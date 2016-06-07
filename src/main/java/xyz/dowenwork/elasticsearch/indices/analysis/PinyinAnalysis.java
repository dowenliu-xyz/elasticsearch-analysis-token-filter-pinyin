package xyz.dowenwork.elasticsearch.indices.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.PreBuiltTokenFilterFactoryFactory;
import org.elasticsearch.index.analysis.TokenFilterFactory;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import xyz.dowenwork.lucene.analyzer.PinyinTransformTokenFilter;

import java.util.Map;

/**
 * <p>create at 16-6-6</p>
 *
 * @author liufl
 * @since 2.3.3.0
 */
public class PinyinAnalysis extends AbstractComponent {
    @Inject
    public PinyinAnalysis(Settings settings, IndicesAnalysisService indicesAnalysisService) {
        super(settings, PinyinAnalysis.class);
        indicesAnalysisService.tokenFilterFactories().put("pinyin", new PreBuiltTokenFilterFactoryFactory(
                new TokenFilterFactory() {
                    @Override
                    public String name() {
                        return "pinyin";
                    }

                    @Override
                    public TokenStream create(TokenStream tokenStream) {
                        int type = PinyinAnalysis.this.settings
                                .getAsInt("index.analysis.filter.pinyin.transformType", PinyinTransformTokenFilter.TYPE_PINYIN);
                        if (type < 0x01 || type > 0x03) {
                            logger.warn("pinyin transforming type illegal: {}, use default value {} instead", type,
                                    PinyinTransformTokenFilter.TYPE_PINYIN);
                            type = PinyinTransformTokenFilter.TYPE_PINYIN;
                        }
                        boolean keepOrigin = PinyinAnalysis.this.settings.getAsBoolean("index.analysis.filter.pinyin.keepOrigin", true);
                        int minTermLength = PinyinAnalysis.this.settings.getAsInt("index.analysis.filter.pinyin.minTermLength", 2);
                        if (minTermLength < 1) {
                            logger.warn(
                                    "min term length for pinyin transforming illegal: {}, use default value {} instead",
                                    minTermLength, 2);
                            minTermLength = 2;
                        }
                        if (minTermLength > 2) {
                            logger.info(
                                    "value 2 is recommended for min term length for pinyin transforming, your value set may be too large: {}",
                                    minTermLength);
                        }
                        return new PinyinTransformTokenFilter(tokenStream, type, minTermLength, keepOrigin);
                    }
                }));
    }
}
