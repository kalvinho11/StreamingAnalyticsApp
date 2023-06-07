package com.example.streaminganalytics.infrastructure.database;

import com.example.streaminganalytics.domain.DatabaseSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class DatabaseSequenceGenerator {

    private MongoOperations mongoOperations;

    @Autowired
    public DatabaseSequenceGenerator(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }

    public void deleteAll() {
        mongoOperations.dropCollection("database_sequences");
    }
}
