package mx.wizelinehack.contextenginedemo.conf;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.cloud.aws.core.region.RegionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The type Global config.
 *
 * @author Armando Montoya, created on: 6/2/18
 */
@Configuration
public class GlobalConfig {

    /**
     * Encoder b crypt password encoder.
     *
     * @return the b crypt password encoder
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AmazonS3 s3client(
            final AWSCredentialsProvider awsCredentialsProvider,
            final RegionProvider regionProvider) {

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(regionProvider.getRegion().getName())
                .withCredentials(awsCredentialsProvider)
                .build();
    }

    @Bean
    public TransferManager transferManager(
            final AWSCredentialsProvider awsCredentialsProvider,
            final RegionProvider regionProvider) {

        return TransferManagerBuilder.standard()
                .withS3Client(
                        this.s3client(
                                awsCredentialsProvider, regionProvider
                        )
                )
                .build();
    }

}
