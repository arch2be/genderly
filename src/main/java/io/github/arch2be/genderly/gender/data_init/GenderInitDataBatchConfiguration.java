package io.github.arch2be.genderly.gender.data_init;

import io.github.arch2be.genderly.gender.GenderRepository;
import io.github.arch2be.genderly.gender.GenderToken;
import io.github.arch2be.genderly.gender.GenderType;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
class GenderInitDataBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final GenderRepository genderRepository;

    public GenderInitDataBatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, GenderRepository genderRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.genderRepository = genderRepository;
    }

    @Bean
    public FlatFileItemReader<GenderToken> readerFemaleTokens() {
        return new FlatFileItemReaderBuilder<GenderToken>()
                .name("femaleTokenReader")
                .resource(new ClassPathResource("female_tokens.csv"))
                .delimited()
                .names("name")
                .fieldSetMapper(fieldSet -> new GenderToken(fieldSet.readString(0), GenderType.FEMALE))
                .build();
    }

    @Bean
    public FlatFileItemReader<GenderToken> readerMaleTokens() {
        return new FlatFileItemReaderBuilder<GenderToken>()
                .name("maleTokenReader")
                .resource(new ClassPathResource("male_tokens.csv"))
                .delimited()
                .names("name")
                .fieldSetMapper(fieldSet -> new GenderToken(fieldSet.readString(0), GenderType.MALE))
                .build();
    }

    @Bean
    public ItemWriter<GenderToken> writer() {
        return genderRepository::saveAll;
    }

    @Bean
    public Job importFemaleTokens(Step stepForFemaleTokens) {
        return jobBuilderFactory.get("femaleTokenReader")
                .incrementer(new RunIdIncrementer())
                .flow(stepForFemaleTokens)
                .end()
                .build();
    }

    @Bean
    public Step stepForFemaleTokens(ItemWriter<GenderToken> writer) {
        return stepBuilderFactory.get("stepForFemaleTokens")
                .<GenderToken, GenderToken> chunk(10)
                .reader(readerFemaleTokens())
                .writer(writer)
                .build();
    }

    @Bean
    public Job importMaleTokens(Step stepForMaleTokens) {
        return jobBuilderFactory.get("maleTokenReader")
                .incrementer(new RunIdIncrementer())
                .flow(stepForMaleTokens)
                .end()
                .build();
    }

    @Bean
    public Step stepForMaleTokens(ItemWriter<GenderToken> writer) {
        return stepBuilderFactory.get("stepForMaleTokens")
                .<GenderToken, GenderToken> chunk(10)
                .reader(readerMaleTokens())
                .writer(writer)
                .build();
    }
}
