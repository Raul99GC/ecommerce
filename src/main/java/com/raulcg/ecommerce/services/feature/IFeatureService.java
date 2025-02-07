package com.raulcg.ecommerce.services.feature;

import com.raulcg.ecommerce.models.Feature;

import java.util.List;

public interface IFeatureService {
    Feature addFeatureImage(String image);

    List<Feature> getFeatures();
}
