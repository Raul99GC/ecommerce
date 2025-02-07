package com.raulcg.ecommerce.services.feature;

import com.raulcg.ecommerce.models.Feature;
import com.raulcg.ecommerce.repositories.FeatureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService implements IFeatureService {

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @Override
    public Feature addFeatureImage(String image) {
        Feature feature = new Feature();
        feature.setImage(image);
        return featureRepository.save(feature);
    }

    @Override
    public List<Feature> getFeatures() {
        return featureRepository.findAll();
    }
}
