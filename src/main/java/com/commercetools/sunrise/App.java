package com.commercetools.sunrise;

import com.commercetools.sunrise.cms.CmsPage;
import com.commercetools.sunrise.cms.CmsService;
import com.commercetools.sunrise.cms.contentful.ContentfulCmsService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import static com.commercetools.sunrise.PropertiesUtils.loadProperties;

/**
 * Hello world!
 *
 */
public class App {

    private static final List<Locale> LOCALES = Collections.singletonList(Locale.US);

    public static void main(String[] args ) throws ExecutionException, InterruptedException {
        final Properties properties = loadProperties();

        final String spaceId = properties.getProperty("spaceId");
        final String accessToken = properties.getProperty("accessToken");
        final String productContentTypeId = "2PqfXUJwE8qSYKuM0U6w8M";
        final String pageSlugFieldId = "slug";

        final CmsService cmsService = ContentfulCmsService.of(spaceId, accessToken, productContentTypeId, pageSlugFieldId, ForkJoinPool.commonPool());

        printProduct(cmsService, "hudson-wall-cup");
        printProduct(cmsService, "soso-wall-clock");
        printProduct(cmsService, "playsam-streamliner-classic-car-espresso");

    }

    private static void printProduct(final CmsService cmsService, final String pageKey) throws ExecutionException, InterruptedException {
        final CmsPage cmsPage = fetchCmsPage(cmsService, pageKey);
        System.out.println("** PRODUCT " + cmsPage.fieldOrEmpty("productName"));
        System.out.println("Price: " + cmsPage.fieldOrEmpty("price"));
        System.out.println("Category: " + cmsPage.fieldOrEmpty("categories[0].title"));
        System.out.println("Brand logo: " + cmsPage.fieldOrEmpty("brand.logo"));
    }

    private static CmsPage fetchCmsPage(final CmsService cmsService, final String pageKey) throws InterruptedException, ExecutionException {
        return cmsService.page(pageKey, LOCALES).toCompletableFuture().get()
                    .orElseThrow(() -> new RuntimeException("Not found page " + pageKey));
    }
}
