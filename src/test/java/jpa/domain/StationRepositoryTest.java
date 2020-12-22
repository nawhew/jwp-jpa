package jpa.domain;

import jpa.common.JpaAuditingDate;
import jpa.config.JpaAuditingConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE
        , classes = {JpaAuditingConfig.class, JpaAuditingDate.class}
))
public class StationRepositoryTest {

    @Autowired
    StationRepository stationRepository;

    @Test
    @DisplayName("station 저장 테스트")
    public void save() {
        // given
        Station station = new Station("산본역");

        // when
        Station saveStation = this.stationRepository.save(station);

        // then
        assertAll(
                () -> assertThat(saveStation.getId()).isNotNull(),
                () -> assertEquals(saveStation, station),
                () -> assertThat(saveStation.getCreatedDate()).isNotNull()
        );
    }

    @Test
    @DisplayName("이름으로 station 조회 테스트")
    public void findByName() {
        // given
        String name = "산본역";
        stationRepository.save(new Station(name));

        // when
        Station stationFindByName = this.stationRepository.findByName(name);

        // then
        assertThat(stationFindByName.getName()).isEqualTo(name);
    }
}
