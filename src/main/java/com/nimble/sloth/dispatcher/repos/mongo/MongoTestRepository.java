package com.nimble.sloth.dispatcher.repos.mongo;

import com.nimble.sloth.dispatcher.func.test.TestObject;
import com.nimble.sloth.dispatcher.func.test.TestRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class MongoTestRepository implements TestRepository {

    private final MongoTemplate template;

    public MongoTestRepository(final MongoTemplate template) {
        this.template = template;
    }

    @Override
    public TestObject findByName(final String name) {
        final Query query = new Query(where("test").is(name));
        return template.findOne(query, TestObject.class, "test");
    }
}
