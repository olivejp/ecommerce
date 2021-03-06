package nc.oliweb.service.impl;

import nc.oliweb.domain.Category;
import nc.oliweb.repository.CategoryRepository;
import nc.oliweb.repository.search.CategorySearchRepository;
import nc.oliweb.service.CategoryService;
import nc.oliweb.service.dto.CategoryDTO;
import nc.oliweb.service.mapper.CategoryMapper;
import nc.oliweb.web.rest.errors.CategoryAlertException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final CategorySearchRepository categorySearchRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CategorySearchRepository categorySearchRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categorySearchRepository = categorySearchRepository;
    }

    private void grabFamily(Long idToSearch, Set<Long> listToComplete) {
        if (listToComplete == null) {
            listToComplete = new HashSet<>();
        }
        List<Category> children = categoryRepository.findAllByCategoryParent_Id(idToSearch);
        for (Category child : children) {
            listToComplete.add(child.getId());
            grabFamily(child.getId(), listToComplete);
        }
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) throws CategoryAlertException {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);

        if (category.getId() != null) {
            Set<Long> setIds = new HashSet<>();
            grabFamily(category.getId(), setIds);

            if (!setIds.isEmpty() && setIds.contains(categoryDTO.getCategoryParentId())) {
                throw new CategoryAlertException("La catégorie parent ne peut pas être un des enfants", "Category", "categoryParentError", "categoryParentId");
            }
        }

        category = categoryRepository.save(category);
        CategoryDTO result = categoryMapper.toDto(category);
        categorySearchRepository.save(category);
        return result;
    }

    /**
     * Get all the categories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        log.debug("Request to get all Categories");
        return categoryRepository.findAllOrderById().stream()
            .map(categoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one category by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryDTO> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id)
            .map(categoryMapper::toDto);
    }

    /**
     * Delete the category by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
        categorySearchRepository.deleteById(id);
    }

    /**
     * Search for the category corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> search(String query) {
        log.debug("Request to search Categories for query {}", query);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(wildcardQuery("name", "*" + query + "*"));

        return StreamSupport
            .stream(categorySearchRepository.search(boolQueryBuilder).spliterator(), false)
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> searchQueryString(String query) {
        log.debug("Request to search Categories for query {}", query);
        return StreamSupport
            .stream(categorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(categoryMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public void reindex() {
        log.debug("Request to reindex Categories");
        categoryRepository.findAll().forEach(categorySearchRepository::save);
    }

    @Override
    public List<CategoryDTO> getAvailableParents(long idCategory) {
        log.debug("Request to get all the available parents");
        Set<Long> idsFamily = new HashSet<>();
        idsFamily.add(idCategory);
        grabFamily(idCategory, idsFamily);
        return categoryRepository.findAllByCategoryParentIdIsNotIn(new ArrayList<>(idsFamily))
            .stream().map(categoryMapper::toDto)
            .collect(Collectors.toList());
    }
}
