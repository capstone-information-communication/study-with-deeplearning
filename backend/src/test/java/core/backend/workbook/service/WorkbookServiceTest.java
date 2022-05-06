package core.backend.workbook.service;

import core.backend.workbook.domain.Workbook;
import core.backend.workbook.dto.WorkbookCondition;
import core.backend.workbook.dto.WorkbookUpdateRequestDto;
import core.backend.workbook.exception.WorkbookNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class WorkbookServiceTest {

    @Autowired
    private WorkbookService workbookService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("[WorkbookService] save")
    public void saveTest() {
        //given
        Workbook workbook = Workbook.builder()
                .memberId(1L)
                .description("설명")
                .title("제목")
                .build();

        //when
        Long id = workbookService.save(workbook);
        Workbook findWorkbook = workbookService.findByIdOrThrow(id);

        //then
        assertThat(findWorkbook).isInstanceOf(Workbook.class);
        assertThat(workbook.getId()).isEqualTo(findWorkbook.getId());
        assertThat(workbook.getTitle()).isEqualTo(findWorkbook.getTitle());
        assertThat(workbook.getDescription()).isEqualTo(findWorkbook.getDescription());
        assertThat(workbook.getLikeCount()).isEqualTo(0);
        assertThat(workbook.getMemberId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("[WorkbookService] update")
    public void updateTest() {
        //given
        Workbook workbook = Workbook.builder()
                .memberId(1L)
                .description("설명")
                .title("제목")
                .build();

        WorkbookUpdateRequestDto dto = new WorkbookUpdateRequestDto(
                "제목 수정", "설명 수정");
        em.persist(workbook);
        //when

        Long id = workbookService.update(workbook, dto);
        Workbook updateWorkbook = workbookService.findByIdOrThrow(id);

        //then
        assertThat(updateWorkbook.getId()).isEqualTo(id);
        assertThat(updateWorkbook.getTitle()).isEqualTo(dto.getTitle());
        assertThat(updateWorkbook.getDescription()).isEqualTo(dto.getDescription());
    }

    @Test
    @DisplayName("[WorkbookService] findByTitle")
    public void findByTitleTest() {
        //given
        Workbook workbook = Workbook.builder()
                .memberId(1L)
                .description("설명")
                .title("제목")
                .build();
        em.persist(workbook);

        //when
        Workbook findWorkbook = workbookService.findByTitle("제목").get();

        //then
        assertThat(workbook.getId()).isEqualTo(findWorkbook.getId());
        assertThat(workbook.getTitle()).isEqualTo(findWorkbook.getTitle());
    }

    @Test
    @DisplayName("[WorkbookService] search")
    public void searchTest() {
        //given
        for (int i = 0; i < 5; i++) {
            Workbook workbook = Workbook.builder()
                    .memberId(1L)
                    .description("description" + i)
                    .title("title" + i)
                    .build();
            em.persist(workbook);
        }
        WorkbookCondition condition = new WorkbookCondition("title", "");
        WorkbookCondition titleCond = new WorkbookCondition("title1", "");

        //when
        Page<Workbook> workbookList = workbookService.search(condition, Pageable.ofSize(5));
        Workbook workbook = workbookService.search(titleCond, Pageable.ofSize(5))
                .stream().findAny().get();

        //then
        assertThat(workbookList.getSize()).isEqualTo(5);
        assertThat(workbook.getTitle()).isEqualTo(titleCond.getTitle());
    }

    @Test
    @DisplayName("[WorkbookService] findAll")
    public void findAllTest() {
        //given
        for (int i = 0; i < 5; i++) {
            Workbook workbook = Workbook.builder()
                    .memberId(1L)
                    .description("description" + i)
                    .title("title" + i)
                    .build();
            em.persist(workbook);
        }

        //when
        Page<Workbook> workbookList = workbookService.findAll(Pageable.ofSize(5));

        //then
        assertThat(workbookList.getSize()).isEqualTo(5);
    }

    @Test
    @DisplayName("[WorkbookService] deleteById")
    public void deleteByIdTest() {
        //given
        Workbook workbook = Workbook.builder()
                .memberId(1L)
                .description("설명")
                .title("제목")
                .build();
        em.persist(workbook);

        //when
        workbookService.deleteById(workbook.getId());

        //then
        Assertions.assertThrows(WorkbookNotFoundException.class, () -> {
            workbookService.findByIdOrThrow(workbook.getId());
        });
    }

    @Test
    @DisplayName("[WorkbookService] findAllWithOrderBy")
    public void findAllWithOrderByTest() {
        //given
        for (int i = 0; i < 5; i++) {
            Workbook workbook = Workbook.builder()
                    .memberId(1L)
                    .description("description" + i)
                    .title("title" + i)
                    .build();
            em.persist(workbook);
        }

        //when
        List<Workbook> workbookList = workbookService.findAllOrderedBy("title", Pageable.ofSize(5)).toList();

        //then
        for (int i = 0; i < 5; i++) {
            assertThat(workbookList.get(i).getTitle()).isEqualTo("title" + i);
        }
    }
}