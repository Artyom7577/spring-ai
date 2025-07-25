package com.polixis.springai.bootstrap;

import java.util.List;
import java.util.Objects;

import com.polixis.springai.rag.config.VectorStoreProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadVectorStore implements CommandLineRunner {

    private final VectorStore vectorStore;
    private final VectorStoreProperties vectorStoreProperties;

    private static final Logger log = LoggerFactory.getLogger(LoadVectorStore.class);

    public void run(String... args) throws Exception {
        log.info("Loading vector store...");
        if (Objects.requireNonNull(vectorStore.similaritySearch("Sportsman")).isEmpty()) {
            log.info("Vector store is empty, loading documents from: {}", vectorStoreProperties.getDocumentsToLoad());
            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.info("Loading document: {}", document.getFilename());
                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.split(documents);

                vectorStore.add(splitDocs);
            });
            log.info("Vector store loaded successfully.");
        } else {
            log.info("Vector store already contains data, skipping loading.");
        }
    }
}
