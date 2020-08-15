package nc.oliweb.service.impl;

import nc.oliweb.service.PhotoService;
import nc.oliweb.domain.Photo;
import nc.oliweb.repository.PhotoRepository;
import nc.oliweb.repository.search.PhotoSearchRepository;
import nc.oliweb.service.dto.PhotoDTO;
import nc.oliweb.service.mapper.PhotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Photo}.
 */
@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final Logger log = LoggerFactory.getLogger(PhotoServiceImpl.class);

    private final PhotoRepository photoRepository;

    private final PhotoMapper photoMapper;

    private final PhotoSearchRepository photoSearchRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper, PhotoSearchRepository photoSearchRepository) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
        this.photoSearchRepository = photoSearchRepository;
    }

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PhotoDTO save(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        photo = photoRepository.save(photo);
        PhotoDTO result = photoMapper.toDto(photo);
        photoSearchRepository.save(photo);
        return result;
    }

    /**
     * Get all the photos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PhotoDTO> findAll() {
        log.debug("Request to get all Photos");
        return photoRepository.findAll().stream()
            .map(photoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one photo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PhotoDTO> findOne(Long id) {
        log.debug("Request to get Photo : {}", id);
        return photoRepository.findById(id)
            .map(photoMapper::toDto);
    }

    /**
     * Delete the photo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.deleteById(id);
        photoSearchRepository.deleteById(id);
    }

    /**
     * Search for the photo corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PhotoDTO> search(String query) {
        log.debug("Request to search Photos for query {}", query);
        return StreamSupport
            .stream(photoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(photoMapper::toDto)
        .collect(Collectors.toList());
    }
}
