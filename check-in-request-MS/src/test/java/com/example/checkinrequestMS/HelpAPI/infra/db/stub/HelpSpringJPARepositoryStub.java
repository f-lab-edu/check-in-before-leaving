package com.example.checkinrequestMS.HelpAPI.infra.db.stub;

import com.example.checkinrequestMS.HelpAPI.infra.db.entity.HelpJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.CheckInJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.EtcJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.entity.child.LineUpJPAEntity;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.HelpJPARepository;
import com.example.checkinrequestMS.HelpAPI.infra.db.help.HelpSpringJPARepository;
import com.example.checkinrequestMS.fixtures.HelpAPI.infra.entity.CheckInJPAEntityFixture;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@NoRepositoryBean
public class HelpSpringJPARepositoryStub implements HelpSpringJPARepository {

    Map<Long, HelpJPAEntity> jpaEntities = new HashMap<>();
    AtomicLong ids = new AtomicLong(1);

    @Override
    public <S extends HelpJPAEntity> S save(S entity) {
        Long id = ids.getAndIncrement();

        if(entity instanceof CheckInJPAEntity){
            CheckInJPAEntity checkInJPAEntity = CheckInJPAEntityFixture.createWithId(id);
            jpaEntities.put(checkInJPAEntity.getId(), checkInJPAEntity);
            return (S) checkInJPAEntity;
        }else if(entity instanceof LineUpJPAEntity){
            throw new StubNotExistException("save");
        }else if(entity instanceof EtcJPAEntity){
            throw new StubNotExistException("save");
        }
        throw new RuntimeException("HelpJPAEntity not found");
    }

    @Override
    public Optional<HelpJPAEntity> findById(Long id) {
        return Optional.ofNullable(jpaEntities.get(id));
    }


    @Override
    public void flush() {
        throw new StubNotExistException("flush");
    }

    @Override
    public <S extends HelpJPAEntity> S saveAndFlush(S entity) {
        throw new StubNotExistException("saveAndFlush");
    }

    @Override
    public <S extends HelpJPAEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new StubNotExistException("saveAllAndFlush");
    }

    @Override
    public void deleteAllInBatch(Iterable<HelpJPAEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {
    }

    @Override
    public HelpJPAEntity getOne(Long aLong) {
        throw new StubNotExistException("getOne");
    }

    @Override
    public HelpJPAEntity getById(Long aLong) {
        throw new StubNotExistException("getById");
    }

    @Override
    public HelpJPAEntity getReferenceById(Long aLong) {
        throw new StubNotExistException("getReferenceById");
    }

    @Override
    public <S extends HelpJPAEntity> Optional<S> findOne(Example<S> example) {
        throw new StubNotExistException("findOne");
    }

    @Override
    public <S extends HelpJPAEntity> List<S> findAll(Example<S> example) {
         throw new StubNotExistException("findAll");
    }

    @Override
    public <S extends HelpJPAEntity> List<S> findAll(Example<S> example, Sort sort) {
         throw new StubNotExistException("findAll - sort");
    }

    @Override
    public <S extends HelpJPAEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new StubNotExistException("findAll - pageable");
    }

    @Override
    public <S extends HelpJPAEntity> long count(Example<S> example) {
        throw new StubNotExistException("count");
    }

    @Override
    public <S extends HelpJPAEntity> boolean exists(Example<S> example) {
            throw new StubNotExistException("exists");
    }

    @Override
    public <S extends HelpJPAEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new StubNotExistException("findBy");
    }


    @Override
    public <S extends HelpJPAEntity> List<S> saveAll(Iterable<S> entities) {
         throw new StubNotExistException("saveAll");
    }

    @Override
    public boolean existsById(Long aLong) {
         throw new StubNotExistException("existsById");
    }

    @Override
    public List<HelpJPAEntity> findAll() {
         throw new StubNotExistException("findAll");
    }

    @Override
    public List<HelpJPAEntity> findAllById(Iterable<Long> longs) {
         throw new StubNotExistException("findAllById");
    }

    @Override
    public long count() {
        throw new StubNotExistException("count");
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(HelpJPAEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends HelpJPAEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<HelpJPAEntity> findAll(Sort sort) {
         throw new StubNotExistException("findAll");
    }

    @Override
    public Page<HelpJPAEntity> findAll(Pageable pageable) {
        throw new StubNotExistException("findAll");
    }
}
