package edu.kit.iti.scale.lara.backend.api;

import edu.kit.iti.scale.lara.backend.model.research.paper.Author;

import java.util.List;

/**
 * Represents a Paper for the API
 *
 * @author unqkm
 */
public record ApiPaper(List<Author> authors,
                       String id,
                       String title,
                       int year,
                       String abstractText,
                       int citationCount,
                       int referenceCount,
                       String venue,
                       String pdfUrl) {
}
