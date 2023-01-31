package edu.kit.iti.scale.lara.backend.api.semanticscolar;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.kit.iti.scale.lara.backend.api.ApiPaper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SemanticScholarWrapperTest {

    String jsonPaper = "{\n" +
            "    \"paperId\": \"649def34f8be52c8b66281af98ae884c09aef38b\",\n" +
            "    \"url\": \"https://www.semanticscholar.org/paper/649def34f8be52c8b66281af98ae884c09aef38b\",\n" +
            "    \"title\": \"Construction of the Literature Graph in Semantic Scholar\",\n" +
            "    \"abstract\": \"We describe a deployed scalable system for organizing published scientific literature into a heterogeneous graph to facilitate algorithmic manipulation and discovery. The resulting literature graph consists of more than 280M nodes, representing papers, authors, entities and various interactions between them (e.g., authorships, citations, entity mentions). We reduce literature graph construction into familiar NLP tasks (e.g., entity extraction and linking), point out research challenges due to differences from standard formulations of these tasks, and report empirical results for each task. The methods described in this paper are used to enable semantic features in www.semanticscholar.org.\",\n" +
            "    \"venue\": \"North American Chapter of the Association for Computational Linguistics\",\n" +
            "    \"year\": 2018,\n" +
            "    \"referenceCount\": 27,\n" +
            "    \"citationCount\": 286,\n" +
            "    \"authors\": [\n" +
            "        {\n" +
            "            \"authorId\": \"145585097\",\n" +
            "            \"name\": \"Waleed Ammar\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"3458736\",\n" +
            "            \"name\": \"Dirk Groeneveld\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"1857797\",\n" +
            "            \"name\": \"Chandra Bhagavatula\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46181066\",\n" +
            "            \"name\": \"Iz Beltagy\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46230609\",\n" +
            "            \"name\": \"Miles Crawford\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"145612610\",\n" +
            "            \"name\": \"Doug Downey\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"38092776\",\n" +
            "            \"name\": \"Jason Dunkelberger\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"143718836\",\n" +
            "            \"name\": \"Ahmed Elgohary\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46411828\",\n" +
            "            \"name\": \"Sergey Feldman\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"4480314\",\n" +
            "            \"name\": \"Vu A. Ha\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"143967880\",\n" +
            "            \"name\": \"Rodney Michael Kinney\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"41018147\",\n" +
            "            \"name\": \"Sebastian Kohlmeier\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46258841\",\n" +
            "            \"name\": \"Kyle Lo\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"144240185\",\n" +
            "            \"name\": \"Tyler C. Murray\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46256862\",\n" +
            "            \"name\": \"Hsu-Han Ooi\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"39139825\",\n" +
            "            \"name\": \"Matthew E. Peters\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"39561369\",\n" +
            "            \"name\": \"Joanna L. Power\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46181683\",\n" +
            "            \"name\": \"Sam Skjonsberg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"31860505\",\n" +
            "            \"name\": \"Lucy Lu Wang\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"46212260\",\n" +
            "            \"name\": \"Christopher Wilhelm\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"2112339497\",\n" +
            "            \"name\": \"Zheng Yuan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"15292561\",\n" +
            "            \"name\": \"Madeleine van Zuylen\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"authorId\": \"1741101\",\n" +
            "            \"name\": \"Oren Etzioni\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    String jsonReferences = "{\n" +
            "    \"offset\": 0,\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"3493c33b36f005d230469c456256cb1fe144376f\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/3493c33b36f005d230469c456256cb1fe144376f\",\n" +
            "                \"title\": \"An NLP Curator (or: How I Learned to Stop Worrying and Love NLP Pipelines)\",\n" +
            "                \"abstract\": \"Natural Language Processing continues to grow in popularity in a range of research and commercial applications, yet managing the wide array of potential NLP components remains a difficult problem. This paper describes Curator, an NLP management framework designed to address some common problems and inefficiencies associated with building NLP process pipelines; and Edison, an NLP data structure library in Java that provides streamlined interactions with Curator and offers a range of useful supporting functionality.\",\n" +
            "                \"venue\": \"International Conference on Language Resources and Evaluation\",\n" +
            "                \"year\": 2012,\n" +
            "                \"referenceCount\": 13,\n" +
            "                \"citationCount\": 55,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"50649676\",\n" +
            "                        \"name\": \"J. Clarke\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3052879\",\n" +
            "                        \"name\": \"Vivek Srikumar\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3149169\",\n" +
            "                        \"name\": \"M. Sammons\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144590225\",\n" +
            "                        \"name\": \"D. Roth\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"609ed85b5ae171131c6d89e68aaf93505b73dac4\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/609ed85b5ae171131c6d89e68aaf93505b73dac4\",\n" +
            "                \"title\": \"Learning English Light Verb Constructions: Contextual or Statistical\",\n" +
            "                \"abstract\": \"In this paper, we investigate a supervised machine learning framework for automatically learning of English Light Verb Constructions (LVCs). Our system achieves an 86.3% accuracy with a baseline (chance) performance of 52.2% when trained with groups of either contextual or statistical features. In addition, we present an in-depth analysis of these contextual and statistical features and show that the system trained by these two types of cosmetically different features reaches similar performance empirically. However, in the situation where the surface structures of candidate LVCs are identical, the system trained with contextual features which contain information on surrounding words performs 16.7% better. \\n \\nIn this study, we also construct a balanced benchmark dataset with 2,162 sentences from BNC for English LVCs. And this data set is publicly available and is also a useful computational resource for research on MWEs in general.\",\n" +
            "                \"venue\": \"MWE@ACL\",\n" +
            "                \"year\": 2011,\n" +
            "                \"referenceCount\": 35,\n" +
            "                \"citationCount\": 59,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2836175\",\n" +
            "                        \"name\": \"Yuancheng Tu\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144590225\",\n" +
            "                        \"name\": \"D. Roth\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"273dfbcb68080251f5e9ff38b4413d7bd84b10a1\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/273dfbcb68080251f5e9ff38b4413d7bd84b10a1\",\n" +
            "                \"title\": \"LIBSVM: A library for support vector machines\",\n" +
            "                \"abstract\": \"LIBSVM is a library for Support Vector Machines (SVMs). We have been actively developing this package since the year 2000. The goal is to help users to easily apply SVM to their applications. LIBSVM has gained wide popularity in machine learning and many other areas. In this article, we present all implementation details of LIBSVM. Issues such as solving SVM optimization problems theoretical convergence multiclass classification probability estimates and parameter selection are discussed in detail.\",\n" +
            "                \"venue\": \"TIST\",\n" +
            "                \"year\": 2011,\n" +
            "                \"referenceCount\": 59,\n" +
            "                \"citationCount\": 41871,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": null,\n" +
            "                        \"name\": \"Chih-Chung Chang\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1711460\",\n" +
            "                        \"name\": \"Chih-Jen Lin\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"b18b799d4d7f4ee2ec0f72077fdc7ef449854f6b\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/b18b799d4d7f4ee2ec0f72077fdc7ef449854f6b\",\n" +
            "                \"title\": \"How to pick out token instances of English verb-particle constructions\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"Language Resources and Evaluation\",\n" +
            "                \"year\": 2010,\n" +
            "                \"referenceCount\": 47,\n" +
            "                \"citationCount\": 30,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"1736741380\",\n" +
            "                        \"name\": \"Su Nam Kim\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145465286\",\n" +
            "                        \"name\": \"Timothy Baldwin\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"6608189cc1941748787f9f7948ec9bacb2d2615b\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/6608189cc1941748787f9f7948ec9bacb2d2615b\",\n" +
            "                \"title\": \"Classifying Particle Semantics in English Verb-Particle Constructions\",\n" +
            "                \"abstract\": \"Previous computational work on learning the semantic properties of verb-particle constructions (VPCs) has focused on their compositionality, and has left unaddressed the issue of which meaning of the component words is being used in a given VPC. We develop a feature space for use in classification of the sense contributed by the particle in a VPC, and test this on VPCs using the particle up. The features that capture linguistic properties of VPCs that are relevant to the semantics of the particle outperform linguistically uninformed word co-occurrence features in our experiments on unseen test VPCs.\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2006,\n" +
            "                \"referenceCount\": 33,\n" +
            "                \"citationCount\": 46,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"145130572\",\n" +
            "                        \"name\": \"Paul Cook\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145584212\",\n" +
            "                        \"name\": \"S. Stevenson\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"Computational Linguistics Dimensions of the Syntax and Semantics of Prepositions, chapter Verb-Particel Constructions in the World Wide Web\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"Computational Linguistics Dimensions of the Syntax and Semantics of Prepositions, chapter Verb-Particel Constructions in the World Wide Web\",\n" +
            "                \"year\": 2006,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"Computational Linguistics Di\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2006,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"0ecb33ced5b0976accdf13817151f80568b6fdcb\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/0ecb33ced5b0976accdf13817151f80568b6fdcb\",\n" +
            "                \"title\": \"Coarse-to-Fine n-Best Parsing and MaxEnt Discriminative Reranking\",\n" +
            "                \"abstract\": \"Discriminative reranking is one method for constructing high-performance statistical parsers (Collins, 2000). A discriminative reranker requires a source of candidate parses for each sentence. This paper describes a simple yet novel method for constructing sets of 50-best parses based on a coarse-to-fine generative parser (Charniak, 2000). This method generates 50-best lists that are of substantially higher quality than previously obtainable. We used these parses as the input to a MaxEnt reranker (Johnson et al., 1999; Riezler et al., 2002) that selects the best parse from the set of parses for each sentence, obtaining an f-score of 91.0% on sentences of length 100 or less.\",\n" +
            "                \"venue\": \"Annual Meeting of the Association for Computational Linguistics\",\n" +
            "                \"year\": 2005,\n" +
            "                \"referenceCount\": 22,\n" +
            "                \"citationCount\": 1266,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"1749837\",\n" +
            "                        \"name\": \"Eugene Charniak\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145177220\",\n" +
            "                        \"name\": \"Mark Johnson\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"a2e007387f58174eba86e34a9ea605c850c2f1ee\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/a2e007387f58174eba86e34a9ea605c850c2f1ee\",\n" +
            "                \"title\": \"Verb-Particle Constructions and Lexical Resources\",\n" +
            "                \"abstract\": \"In this paper we investigate the phenomenon of verb-particle constructions, discussing their characteristics and their availability for use with NLP systems. We concentrate in particular on the coverage provided by some electronic resources. Given the constantly growing number of verb-particle combinations, possible ways of extending the coverage of the available resources are investigated, taking into account regular patterns found in some productive combinations of verbs and particles. We discuss, in particular, the use of Levin's (1993) classes of verbs as a means to obtain productive verb-particle constructions, and discuss the issues involved in adopting such an approach.\",\n" +
            "                \"venue\": \"Annual Meeting of the Association for Computational Linguistics\",\n" +
            "                \"year\": 2003,\n" +
            "                \"referenceCount\": 17,\n" +
            "                \"citationCount\": 52,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"145585242\",\n" +
            "                        \"name\": \"Aline Villavicencio\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"3df6c9f6cea7b9217fd2b9388afe8b40029d4fb1\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/3df6c9f6cea7b9217fd2b9388afe8b40029d4fb1\",\n" +
            "                \"title\": \"An Expert Lexicon Approach to Identifying English Phrasal Verbs\",\n" +
            "                \"abstract\": \"Phrasal Verbs are an important feature of the English language. Properly identifying them provides the basis for an English parser to decode the related structures. Phrasal verbs have been a challenge to Natural Language Processing (NLP) because they sit at the borderline between lexicon and syntax. Traditional NLP frameworks that separate the lexicon module from the parser make it difficult to handle this problem properly. This paper presents a finite state approach that integrates a phrasal verb expert lexicon between shallow parsing and deep parsing to handle morpho-syntactic interaction. With precision/recall combined performance benchmarked consistently at 95.8%-97.5%, the Phrasal Verb identification problem has basically been solved with the presented method.\",\n" +
            "                \"venue\": \"Annual Meeting of the Association for Computational Linguistics\",\n" +
            "                \"year\": 2003,\n" +
            "                \"referenceCount\": 15,\n" +
            "                \"citationCount\": 44,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"50135754\",\n" +
            "                        \"name\": \"W. Li\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2155283599\",\n" +
            "                        \"name\": \"Xiuhong Zhang\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144632211\",\n" +
            "                        \"name\": \"Cheng Niu\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2117807644\",\n" +
            "                        \"name\": \"Yuankai Jiang\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1748081\",\n" +
            "                        \"name\": \"R. Srihari\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"834b6e59c12a4a1175286654d235c5a32f9caa2e\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/834b6e59c12a4a1175286654d235c5a32f9caa2e\",\n" +
            "                \"title\": \"Verb-particle constructions in a computational grammar of English\",\n" +
            "                \"abstract\": \"In this paper we investigate the phenomenon of verb-particle constructions, discussing their characteristics and the challenges that they present for a computational grammar. We concentrate our discussion on the treatment adopted in a wide-coverage HPSG grammar: the LinGO ERG. Given the constantly growing number of verb-particle combinations, possible ways of extending this treatment are investigated, taking into account the regular patterns found in some productive combinations of verbs and particles. We analyse possible ways of identifying regular patterns using different resources. One possible way to try to capture these is by means of lexical rules, and we discuss the difficulties encountered when adopting such an approach. We also investigate how to restrict the productivity of lexical rules to deal with subregularities and exceptions to the patterns found.\",\n" +
            "                \"venue\": \"Proceedings of the International Conference on Head-Driven Phrase Structure Grammar\",\n" +
            "                \"year\": 2003,\n" +
            "                \"referenceCount\": 32,\n" +
            "                \"citationCount\": 24,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"145585242\",\n" +
            "                        \"name\": \"Aline Villavicencio\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"15379653\",\n" +
            "                        \"name\": \"Ann A. Copestake\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"5504f4ec7b7b7b074e9dc557beeaf68c76b65540\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/5504f4ec7b7b7b074e9dc557beeaf68c76b65540\",\n" +
            "                \"title\": \"Multiword Expressions: A Pain in the Neck for NLP\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"Conference on Intelligent Text Processing and Computational Linguistics\",\n" +
            "                \"year\": 2002,\n" +
            "                \"referenceCount\": 33,\n" +
            "                \"citationCount\": 1211,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2393013\",\n" +
            "                        \"name\": \"I. Sag\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145465286\",\n" +
            "                        \"name\": \"Timothy Baldwin\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"145440721\",\n" +
            "                        \"name\": \"Francis Bond\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"15379653\",\n" +
            "                        \"name\": \"Ann A. Copestake\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3209288\",\n" +
            "                        \"name\": \"D. Flickinger\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"7fa22a5cb618ebdceba4421b0628005ebaaea2c0\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/7fa22a5cb618ebdceba4421b0628005ebaaea2c0\",\n" +
            "                \"title\": \"English particle constructions, the lexicon, and the autonomy of syntax\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2002,\n" +
            "                \"referenceCount\": 0,\n" +
            "                \"citationCount\": 154,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"1766112\",\n" +
            "                        \"name\": \"R. Jackendoff\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"Proc. of the 3rd International Conference on Intelligent Text Processing and Computational Linguistics\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"Proc. of the 3rd International Conference on Intelligent Text Processing and Computational Linguistics\",\n" +
            "                \"year\": 2002,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"3fab92869cfab684b3ffb1c16a771e9c3b774acd\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/3fab92869cfab684b3ffb1c16a771e9c3b774acd\",\n" +
            "                \"title\": \"The Use of Classifiers in Sequential Inference\",\n" +
            "                \"abstract\": \"We study the problem of combining the outcomes of several different classifiers in a way that provides a coherent inference that satisfies some constraints. In particular, we develop two general approaches for an important subproblem - identifying phrase structure. The first is a Markovian approach that extends standard HMMs to allow the use of a rich observation structure and of general classifiers to model state-observation dependencies. The second is an extension of constraint satisfaction formalisms. We develop efficient combination algorithms under both models and study them experimentally in the context of shallow parsing.\",\n" +
            "                \"venue\": \"NIPS\",\n" +
            "                \"year\": 2001,\n" +
            "                \"referenceCount\": 31,\n" +
            "                \"citationCount\": 222,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2474158\",\n" +
            "                        \"name\": \"Vasin Punyakanok\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144590225\",\n" +
            "                        \"name\": \"D. Roth\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"d87ceda3042f781c341ac17109d1e94a717f5f60\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/d87ceda3042f781c341ac17109d1e94a717f5f60\",\n" +
            "                \"title\": \"WordNet : an electronic lexical database\",\n" +
            "                \"abstract\": \"Part 1 The lexical database: nouns in WordNet, George A. Miller modifiers in WordNet, Katherine J. Miller a semantic network of English verbs, Christiane Fellbaum design and implementation of the WordNet lexical database and searching software, Randee I. Tengi. Part 2: automated discovery of WordNet relations, Marti A. Hearst representing verb alterations in WordNet, Karen T. Kohl et al the formalization of WordNet by methods of relational concept analysis, Uta E. Priss. Part 3 Applications of WordNet: building semantic concordances, Shari Landes et al performance and confidence in a semantic annotation task, Christiane Fellbaum et al WordNet and class-based probabilities, Philip Resnik combining local context and WordNet similarity for word sense identification, Claudia Leacock and Martin Chodorow using WordNet for text retrieval, Ellen M. Voorhees lexical chains as representations of context for the detection and correction of malapropisms, Graeme Hirst and David St-Onge temporal indexing through lexical chaining, Reem Al-Halimi and Rick Kazman COLOR-X - using knowledge from WordNet for conceptual modelling, J.F.M. Burg and R.P. van de Riet knowledge processing on an extended WordNet, Sanda M. Harabagiu and Dan I Moldovan appendix - obtaining and using WordNet.\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2000,\n" +
            "                \"referenceCount\": 7,\n" +
            "                \"citationCount\": 13868,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"1721801\",\n" +
            "                        \"name\": \"C. Fellbaum\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"1998.Understanding English Grammar\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 1998,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"e393f6f5d0affbdf3d4b2f82de99fa75e16376c5\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/e393f6f5d0affbdf3d4b2f82de99fa75e16376c5\",\n" +
            "                \"title\": \"The phrasal verb in English\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 1974,\n" +
            "                \"referenceCount\": 0,\n" +
            "                \"citationCount\": 357,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"50552632\",\n" +
            "                        \"name\": \"D. Bolinger\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": \"df3ff6bd539eaa5dca2804c029b6712c8dcb7ae0\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/df3ff6bd539eaa5dca2804c029b6712c8dcb7ae0\",\n" +
            "                \"title\": \"Understanding English grammar\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 1971,\n" +
            "                \"referenceCount\": 0,\n" +
            "                \"citationCount\": 73,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"69522281\",\n" +
            "                        \"name\": \"速川 和男\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citedPaper\": {\n" +
            "                \"paperId\": null,\n" +
            "                \"url\": null,\n" +
            "                \"title\": \"1971.The Phrasal Verb in English\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 1971,\n" +
            "                \"referenceCount\": null,\n" +
            "                \"citationCount\": null,\n" +
            "                \"authors\": []\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    String jsonCitations = "{\n" +
            "    \"offset\": 0,\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"3b7a73ddc1ebf4dcb29a10865af1246bdbfb2bc6\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/3b7a73ddc1ebf4dcb29a10865af1246bdbfb2bc6\",\n" +
            "                \"title\": \"CardiffNLP-Metaphor at SemEval-2022 Task 2: Targeted Fine-tuning of Transformer-based Language Models for Idiomaticity Detection\",\n" +
            "                \"abstract\": \"This paper describes the experiments ran for SemEval-2022 Task 2, subtask A, zero-shot and one-shot settings for idiomaticity detection. Our main approach is based on fine-tuning transformer-based language models as a baseline to perform binary classification. Our system, CardiffNLP-Metaphor, ranked 8th and 7th (respectively on zero- and one-shot settings on this task. Our main contribution lies in the extensive evaluation of transformer-based language models and various configurations, showing, among others, the potential of large multilingual models over base monolingual models. Moreover, we analyse the impact of various input parameters, which offer interesting insights on how language models work in practice.\",\n" +
            "                \"venue\": \"International Workshop on Semantic Evaluation\",\n" +
            "                \"year\": 2022,\n" +
            "                \"referenceCount\": 17,\n" +
            "                \"citationCount\": 1,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"40450311\",\n" +
            "                        \"name\": \"Joanne Boisson\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1387447871\",\n" +
            "                        \"name\": \"José Camacho-Collados\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2254466\",\n" +
            "                        \"name\": \"Luis Espinosa Anke\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"b3df38236d47d6334357c2ecbde4351fef490fe8\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/b3df38236d47d6334357c2ecbde4351fef490fe8\",\n" +
            "                \"title\": \"AStitchInLanguageModels: Dataset and Methods for the Exploration of Idiomaticity in Pre-Trained Language Models\",\n" +
            "                \"abstract\": \"Despite their success in a variety of NLP tasks, pre-trained language models, due to their heavy reliance on compositionality, fail in effectively capturing the meanings of multiword expressions (MWEs), especially idioms. Therefore, datasets and methods to improve the representation of MWEs are urgently needed. Existing datasets are limited to providing the degree of idiomaticity of expressions along with the literal and, where applicable, (a single) non-literal interpretation of MWEs. This work presents a novel dataset of naturally occurring sentences containing MWEs manu-ally classiﬁed into a ﬁne-grained set of meanings, spanning both English and Portuguese. We use this dataset in two tasks designed to test i) a language model’s ability to detect idiom usage, and ii) the effectiveness of a language model in generating representations of sentences containing idioms. Our experiments demonstrate that, on the task of detecting idiomatic usage, these models perform reason-ably well in the one-shot and few-shot scenarios, but that there is signiﬁcant scope for improvement in the zero-shot scenario. On the task of representing idiomaticity, we ﬁnd that pre-training is not always effective, while ﬁne-tuning could provide a sample efﬁcient method of learning representations of sentences containing MWEs.\",\n" +
            "                \"venue\": \"Conference on Empirical Methods in Natural Language Processing\",\n" +
            "                \"year\": 2021,\n" +
            "                \"referenceCount\": 39,\n" +
            "                \"citationCount\": 22,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"3467205\",\n" +
            "                        \"name\": \"Harish Tayyar Madabushi\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2126066178\",\n" +
            "                        \"name\": \"Edward Gow-Smith\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2797847\",\n" +
            "                        \"name\": \"Carolina Scarton\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2113791974\",\n" +
            "                        \"name\": \"A. Villavicencio\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"7a8e09f1b94ac34d98013371390e9ea8ecc3c793\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/7a8e09f1b94ac34d98013371390e9ea8ecc3c793\",\n" +
            "                \"title\": \"THE SEMANTIC CHARACTERISTICS OF SOME ENGLISH PHRASAL VERBS WITH THE COMPONENT \\\"OUT\\\" IN MODERN ENGLISH\",\n" +
            "                \"abstract\": \"Статья посвящена изучению основных характеристик фразовых глаголов с компонентом \\\"out\\\" в современном английском языке. Все мировые языки на протяжении своей истории подвергаются многочисленным изменениям. Язык развивается и изменяется вместе с его носителями, их культурой, менталитетом и ценностями. На развитие языка влияет множество факторов. В настоящее время проблема описания фразовых глаголов с различными компонентами находится в центре внимания лингвистов. Особый интерес представляет проблема употребления и способов перевода фразовых глаголов с различными компонентами в современном английском языке. В центре внимания статьи находятся некоторые особенности функционирования английских фразовых глаголов с компонентом \\\"out. Актуальность работы обусловлена интересом лингвистической науки к современному состоянию фразовых глаголов, выведению их классификаций, особенностей семантики, а также их употребления и перевода. Изучение некоторых особенностей и характеристик данных фразовых глаголов может способствовать решению этой проблемы. Автор анализирует различные подходы к классификации фразовых глаголов. Особое внимание уделяется семантической характеристике данных фразовых глаголов.\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2020,\n" +
            "                \"referenceCount\": 11,\n" +
            "                \"citationCount\": 0,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"103470209\",\n" +
            "                        \"name\": \"T. Razuvaeva\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"9eb18e5628e22d003e430a2a389c4983e2deee5e\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/9eb18e5628e22d003e430a2a389c4983e2deee5e\",\n" +
            "                \"title\": \"CollFrEn: Rich Bilingual English–French Collocation Resource\",\n" +
            "                \"abstract\": \"Collocations in the sense of idiosyncratic lexical co-occurrences of two syntactically bound words traditionally pose a challenge to language learners and many Natural Language Processing (NLP) applications alike. Reliable ground truth (i.e., ideally manually compiled) resources are thus of high value. We present a manually compiled bilingual English–French collocation resource with 7,480 collocations in English and 6,733 in French. Each collocation is enriched with information that facilitates its downstream exploitation in NLP tasks such as machine translation, word sense disambiguation, natural language generation, relation classification, and so forth. Our proposed enrichment covers: the semantic category of the collocation (its lexical function), its vector space representation (for each individual word as well as their joint collocation embedding), a subcategorization pattern of both its elements, as well as their corresponding BabelNet id, and finally, indices of their occurrences in large scale reference corpora.\",\n" +
            "                \"venue\": \"Workshop on Multiword Expressions\",\n" +
            "                \"year\": 2020,\n" +
            "                \"referenceCount\": 49,\n" +
            "                \"citationCount\": 2,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"3256126\",\n" +
            "                        \"name\": \"Beatríz Fisas\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1403765478\",\n" +
            "                        \"name\": \"Joan Codina-Filbà\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2254466\",\n" +
            "                        \"name\": \"Luis Espinosa Anke\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"9092408\",\n" +
            "                        \"name\": \"L. Wanner\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"dc525639af56eb65245b6c2414dc942bf9622ca3\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/dc525639af56eb65245b6c2414dc942bf9622ca3\",\n" +
            "                \"title\": \"Literal Occurrences of Multiword Expressions: Rare Birds That Cause a Stir\",\n" +
            "                \"abstract\": \"Abstract Multiword expressions can have both idiomatic and literal occurrences. For instance pulling strings can be understood either as making use of one’s influence, or literally. Distinguishing these two cases has been addressed in linguistics and psycholinguistics studies, and is also considered one of the major challenges in MWE processing. We suggest that literal occurrences should be considered in both semantic and syntactic terms, which motivates their study in a treebank. We propose heuristics to automatically pre-identify candidate sentences that might contain literal occurrences of verbal VMWEs, and we apply them to existing treebanks in five typologically different languages: Basque, German, Greek, Polish and Portuguese. We also perform a linguistic study of the literal occurrences extracted by the different heuristics. The results suggest that literal occurrences constitute a rare phenomenon. We also identify some properties that may distinguish them from their idiomatic counterparts. This article is a largely extended version of Savary and Cordeiro (2018).\",\n" +
            "                \"venue\": \"Prague Bulletin of Mathematical Linguistics\",\n" +
            "                \"year\": 2019,\n" +
            "                \"referenceCount\": 33,\n" +
            "                \"citationCount\": 9,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2663884\",\n" +
            "                        \"name\": \"Agata Savary\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3417784\",\n" +
            "                        \"name\": \"S. Cordeiro\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"47190197\",\n" +
            "                        \"name\": \"Timm Lichte\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2325592\",\n" +
            "                        \"name\": \"Carlos Ramisch\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"51234564\",\n" +
            "                        \"name\": \"U. Inurrieta\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2408682\",\n" +
            "                        \"name\": \"Voula Giouli\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"a5ec883e080d858b5df60f1b5d711d514459b1e4\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/a5ec883e080d858b5df60f1b5d711d514459b1e4\",\n" +
            "                \"title\": \"Still a Pain in the Neck: Evaluating Text Representations on Lexical Composition\",\n" +
            "                \"abstract\": \"Building meaningful phrase representations is challenging because phrase meanings are not simply the sum of their constituent meanings. Lexical composition can shift the meanings of the constituent words and introduce implicit information. We tested a broad range of textual representations for their capacity to address these issues. We found that, as expected, contextualized word representations perform better than static word embeddings, more so on detecting meaning shift than in recovering implicit information, in which their performance is still far from that of humans. Our evaluation suite, consisting of six tasks related to lexical composition effects, can serve future research aiming to improve representations.\",\n" +
            "                \"venue\": \"International Conference on Topology, Algebra and Categories in Logic\",\n" +
            "                \"year\": 2019,\n" +
            "                \"referenceCount\": 67,\n" +
            "                \"citationCount\": 54,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"3103343\",\n" +
            "                        \"name\": \"Vered Shwartz\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"7465342\",\n" +
            "                        \"name\": \"Ido Dagan\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"9b48cbcaac4871a8264f9735ba323fe44c682951\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/9b48cbcaac4871a8264f9735ba323fe44c682951\",\n" +
            "                \"title\": \"PARSEME multilingual corpus of verbal multiword expressions\",\n" +
            "                \"abstract\": \"Agata Savary1, Marie Candito2, Verginica BarbuMititelu3, Eduard Bejček4, Fabienne Cap5, Slavomír Čéplö6, Silvio Ricardo Cordeiro7, Gülşen Eryiğit8, Voula Giouli9, Maarten van Gompel10, Yaakov HaCohen-Kerner11, Jolanta Kovalevskaitė12, SimonKrek13, Chaya Liebeskind11, JohannaMonti14, Carla Parra Escartín15, Lonneke van der Plas6, Behrang QasemiZadeh16, Carlos Ramisch7, Federico Sangati17, Ivelina Stoyanova18 & Veronika Vincze19 1Université de Tours (France), 2Université Paris Diderot (France), 3Romanian Academy Research Institute for Artificial Intelligence (Romania), 4Charles University (Czech Republic), 5Uppsala University (Sweden), 6University of Malta (Malta), 7Aix Marseille University (France), 8Istanbul Technical University (Turkey), 9Athena Research Center in Athens (Greece), 10Radboud University in Nijmegen (Netherlands), 11Jerusalem College of Technology (Israel), 12Vytautas Magnus University in Kaunas (Lithuania), 13Jožef Stefan Institute in Ljubljana (Slovenia), 14“L’Orientale” University of Naples (Italy), 15ADAPT Centre, Dublin City University (Ireland), 16University of Düsseldorf (Germany), 17independent researcher (Italy), 18Bulgarian Academy of Sciences in Sofia (Bulgaria), 19Univer-\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2018,\n" +
            "                \"referenceCount\": 83,\n" +
            "                \"citationCount\": 39,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"122862283\",\n" +
            "                        \"name\": \"J. Monti\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2077215474\",\n" +
            "                        \"name\": \"Savary Agata\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2636107\",\n" +
            "                        \"name\": \"Marie Candito\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1745399\",\n" +
            "                        \"name\": \"V. Mititelu\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"117094016\",\n" +
            "                        \"name\": \"Bejček Eduard\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"117615943\",\n" +
            "                        \"name\": \"Cap Fabienne\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"117526793\",\n" +
            "                        \"name\": \"Čéplö Slavomir\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3417784\",\n" +
            "                        \"name\": \"S. Cordeiro\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2082855903\",\n" +
            "                        \"name\": \"Eryiğit Gülşen\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2084787772\",\n" +
            "                        \"name\": \"Voula Giouli\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3218618\",\n" +
            "                        \"name\": \"M. V. Gompel\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"115463236\",\n" +
            "                        \"name\": \"HaCohen-Kerner Yaakov\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"116834802\",\n" +
            "                        \"name\": \"Kovalevskaitė Jolanta\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"116134945\",\n" +
            "                        \"name\": \"Krek Simon\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"115203606\",\n" +
            "                        \"name\": \"Liebeskind Chaya\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2969658\",\n" +
            "                        \"name\": \"Carla Parra Escartín\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3356545\",\n" +
            "                        \"name\": \"Lonneke van der Plas\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"117250721\",\n" +
            "                        \"name\": \"Qasemizadeh Behrang\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"117610782\",\n" +
            "                        \"name\": \"Ramisch Carlos\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1764422\",\n" +
            "                        \"name\": \"Federico Sangati\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"116757421\",\n" +
            "                        \"name\": \"Stoyanova Ivelina\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2067158402\",\n" +
            "                        \"name\": \"Vincze Veronika\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"46db27d65bca54619d5783acba9161a2d8c49efa\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/46db27d65bca54619d5783acba9161a2d8c49efa\",\n" +
            "                \"title\": \"Extraction of templates from phrases using Sequence Binary Decision Diagrams\",\n" +
            "                \"abstract\": \"Abstract The extraction of templates such as ‘regard X as Y’ from a set of related phrases requires the identification of their internal structures. This paper presents an unsupervised approach for extracting templates on-the-fly from only tagged text by using a novel relaxed variant of the Sequence Binary Decision Diagram (SeqBDD). A SeqBDD can compress a set of sequences into a graphical structure equivalent to a minimal deterministic finite state automata, but more compact and better suited to the task of template extraction. The main contribution of this paper is a relaxed form of the SeqBDD construction algorithm that enables it to form general representations from a small amount of data. The process of compression of shared structures in the text during Relaxed SeqBDD construction, naturally induces the templates we wish to extract. Experiments show that the method is capable of high-quality extraction on tasks based on verb+preposition templates from corpora and phrasal templates from short messages from social media.\",\n" +
            "                \"venue\": \"Natural Language Engineering\",\n" +
            "                \"year\": 2018,\n" +
            "                \"referenceCount\": 60,\n" +
            "                \"citationCount\": 2,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2081874823\",\n" +
            "                        \"name\": \"D. Hirano\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"6830931\",\n" +
            "                        \"name\": \"Kumiko Tanaka-Ishii\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2987548\",\n" +
            "                        \"name\": \"A. Finch\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"b902beee1aea4ae62e3e4a7e1ad99e5e3962d995\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/b902beee1aea4ae62e3e4a7e1ad99e5e3962d995\",\n" +
            "                \"title\": \"Chapter 4 PARSEME multilingual corpus of verbal multiword expressions\",\n" +
            "                \"abstract\": \"Agata Savary1, Marie Candito2, Verginica BarbuMititelu3, Eduard Bejček4, Fabienne Cap5, Slavomír Čéplö6, Silvio Ricardo Cordeiro7, Gülşen Eryiğit8, Voula Giouli9, Maarten van Gompel10, Yaakov HaCohen-Kerner11, Jolanta Kovalevskaitė12, SimonKrek13, Chaya Liebeskind11, JohannaMonti14, Carla Parra Escartín15, Lonneke van der Plas6, Behrang QasemiZadeh16, Carlos Ramisch7, Federico Sangati17, Ivelina Stoyanova18 & Veronika Vincze19 1Université de Tours (France), 2Université Paris Diderot (France), 3Romanian Academy Research Institute for Artificial Intelligence (Romania), 4Charles University (Czech Republic), 5Uppsala University (Sweden), 6University of Malta (Malta), 7Aix Marseille University (France), 8Istanbul Technical University (Turkey), 9Athena Research Center in Athens (Greece), 10Radboud University in Nijmegen (Netherlands), 11Jerusalem College of Technology (Israel), 12Vytautas Magnus University in Kaunas (Lithuania), 13Jožef Stefan Institute in Ljubljana (Slovenia), 14“L’Orientale” University of Naples (Italy), 15ADAPT Centre, Dublin City University (Ireland), 16University of Düsseldorf (Germany), 17independent researcher (Italy), 18Bulgarian Academy of Sciences in Sofia (Bulgaria), 19Univer-\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2018,\n" +
            "                \"referenceCount\": 49,\n" +
            "                \"citationCount\": 0,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2663884\",\n" +
            "                        \"name\": \"Agata Savary\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2636107\",\n" +
            "                        \"name\": \"Marie Candito\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2103550350\",\n" +
            "                        \"name\": \"Verginica BarbuMititelu\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2082743867\",\n" +
            "                        \"name\": \"-. Ed\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2098089610\",\n" +
            "                        \"name\": \"uard Bejček\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2593268\",\n" +
            "                        \"name\": \"Fabienne Cap\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"67299460\",\n" +
            "                        \"name\": \"S. Čéplö\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"108258350\",\n" +
            "                        \"name\": \"Silvio Ricardo\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2081261192\",\n" +
            "                        \"name\": \"Cordeiro\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3348151\",\n" +
            "                        \"name\": \"G. Eryigit\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2408682\",\n" +
            "                        \"name\": \"Voula Giouli\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1401813543\",\n" +
            "                        \"name\": \"Yaakov Hacohen-Kerner\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2430860\",\n" +
            "                        \"name\": \"J. Kovalevskaite\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2102872512\",\n" +
            "                        \"name\": \"-. Si\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2102485134\",\n" +
            "                        \"name\": \"monKrek\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2601200\",\n" +
            "                        \"name\": \"Chaya Liebeskind\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2100931332\",\n" +
            "                        \"name\": \"JohannaMonti\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2056617210\",\n" +
            "                        \"name\": \"Carla Parra\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2083192363\",\n" +
            "                        \"name\": \"Escartín\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3356545\",\n" +
            "                        \"name\": \"Lonneke van der Plas\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"133719748\",\n" +
            "                        \"name\": \"Behrang Qasemizadeh\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2325592\",\n" +
            "                        \"name\": \"Carlos Ramisch\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1764422\",\n" +
            "                        \"name\": \"Federico Sangati\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"35061489\",\n" +
            "                        \"name\": \"I. Stoyanova\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2536091\",\n" +
            "                        \"name\": \"V. Vincze\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"df931a78442bcfd026681ddd98056e561f3b3443\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/df931a78442bcfd026681ddd98056e561f3b3443\",\n" +
            "                \"title\": \"Putting the Horses Before the Cart: Identifying Multiword Expressions Before Translation\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"International Conference on Computational and Corpus-Based Phraseology\",\n" +
            "                \"year\": 2017,\n" +
            "                \"referenceCount\": 46,\n" +
            "                \"citationCount\": 2,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2325592\",\n" +
            "                        \"name\": \"Carlos Ramisch\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"741d7ef4de4919a46544ff0451c61a5213c8b0a7\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/741d7ef4de4919a46544ff0451c61a5213c8b0a7\",\n" +
            "                \"title\": \"The PARSEME Shared Task on Automatic Identification of Verbal Multiword Expressions\",\n" +
            "                \"abstract\": \"Multiword expressions (MWEs) are known as a “pain in the neck” for NLP due to their idiosyncratic behaviour. While some categories of MWEs have been addressed by many studies, verbal MWEs (VMWEs), such as to take a decision, to break one’s heart or to turn off, have been rarely modelled. This is notably due to their syntactic variability, which hinders treating them as “words with spaces”. We describe an initiative meant to bring about substantial progress in understanding, modelling and processing VMWEs. It is a joint effort, carried out within a European research network, to elaborate universal terminologies and annotation guidelines for 18 languages. Its main outcome is a multilingual 5-million-word annotated corpus which underlies a shared task on automatic identification of VMWEs. This paper presents the corpus annotation methodology and outcome, the shared task organisation and the results of the participating systems.\",\n" +
            "                \"venue\": \"MWE@EACL\",\n" +
            "                \"year\": 2017,\n" +
            "                \"referenceCount\": 68,\n" +
            "                \"citationCount\": 111,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2663884\",\n" +
            "                        \"name\": \"Agata Savary\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2325592\",\n" +
            "                        \"name\": \"Carlos Ramisch\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3417784\",\n" +
            "                        \"name\": \"S. Cordeiro\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1764422\",\n" +
            "                        \"name\": \"Federico Sangati\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2536091\",\n" +
            "                        \"name\": \"V. Vincze\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1746326\",\n" +
            "                        \"name\": \"B. Zadeh\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2636107\",\n" +
            "                        \"name\": \"Marie Candito\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2593268\",\n" +
            "                        \"name\": \"Fabienne Cap\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2408682\",\n" +
            "                        \"name\": \"Voula Giouli\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"35061489\",\n" +
            "                        \"name\": \"I. Stoyanova\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"34796546\",\n" +
            "                        \"name\": \"A. Doucet\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"9d1c44e8740d812c2385694c00fa884bd0cf285c\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/9d1c44e8740d812c2385694c00fa884bd0cf285c\",\n" +
            "                \"title\": \"Computational and Corpus-based Phraseology Recent advances and interdisciplinary approaches Workshop Chairs\",\n" +
            "                \"abstract\": \"Putting the Horses before the Cart: Identifying Multiword Expressions before Translation Translating multiword expressions (MWEs) is notoriously difficult. Part of the challenge stems from the analysis of non-compositional expressions in source texts, preventing literal translation. Therefore, before translating them, it is crucial to locate MWEs in the source text. We would be putting the cart before the horses if we tried to translate MWEs before ensuring that they are correctly identified in the source text. This paper discusses the current state of affairs in automatic MWE identification, covering rule-based methods and sequence taggers. While MWE identification is not a solved problem, significant advances have been made in the recent years. Hence, we can hope that MWE identification can be integrated into MT in the near future, thus avoiding clumsy translations that have often been mocked and used to motivate the urgent need for better MWE processing. iv Proceedings of The 3rd Workshop on Multi-word Units in Machine Translation and Translation Technology (MUMTTT 2017), London, 14 November 2017. Computational and Corpus-based Phraseology Recent advances and interdisciplinary approaches MUMTTT 2017 Academic Programme Tuesday, 14th November 2017 Session 1: Multi-words in Machine Translation Multi-word Adverbs How well are they handled in Parsing and Machine Translation? Martin Volk and Johannes Graën Chinese Separable Words in SMT Gongbo Tang and Fabienne Cap Out of the tailor or still in the woods? An empirical study of MWEs in MT Fabienne Cap Tuesday, 14th November 2017\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2017,\n" +
            "                \"referenceCount\": 42,\n" +
            "                \"citationCount\": 0,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"70340650\",\n" +
            "                        \"name\": \"Gloria Corpas\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"4db59ac2af4a8f73f88bcdf4773e65546e96087b\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/4db59ac2af4a8f73f88bcdf4773e65546e96087b\",\n" +
            "                \"title\": \"Verb-Particle Constructions in Questions\",\n" +
            "                \"abstract\": \"In this paper, we investigate the behavior of verb-particle constructions in English questions. We present a small dataset that contains questions and verb-particle construction candidates. We demonstrate that there are significant differences in the distribution of WH-words, verbs and prepositions/particles in sentences that contain VPCs and sentences that contain only verb + prepositional phrase combinations both by statistical means and in machine learning experiments. Hence, VPCs and non-VPCs can be effectively separated from each other by using a rich feature set, containing several novel features.\",\n" +
            "                \"venue\": \"MWE@EACL\",\n" +
            "                \"year\": 2017,\n" +
            "                \"referenceCount\": 16,\n" +
            "                \"citationCount\": 1,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2536091\",\n" +
            "                        \"name\": \"V. Vincze\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"cb5f1497bd88a9f994a91cd21aad283a4469bde3\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/cb5f1497bd88a9f994a91cd21aad283a4469bde3\",\n" +
            "                \"title\": \"Metaphor: A Computational Perspective\",\n" +
            "                \"abstract\": \"\uE000e literary imagination may take flight on the wings of metaphor, but hard-headed scientists are just as likely as doe-eyed poets to reach for a metaphor when the descriptive need arises. Metaphor is a pervasive aspect of every genre of text and every register of speech, and is as useful for describing the inner workings of a “black hole” (itself a metaphor) as it is the affairs of the human heart. \uE000e ubiquity of metaphor in natural language thus poses a significant challenge for Natural Language Processing (NLP) systems and their builders, who cannot afford to wait until the problems of literal language have been solved before turning their attention to figurative phenomena. \uE000is book offers a comprehensive approach to the computational treatment of metaphor and its figurative brethren—including simile, analogy, and conceptual blending—that does not shy away from their important cognitive and philosophical dimensions. Veale, Shutova, and Beigman Klebanov approach metaphor from multiple computational perspectives, providing coverage of both symbolic and statistical approaches to interpretation and paraphrase generation, while also considering key contributions from philosophy on what constitutes the “meaning” of a metaphor. \uE000is book also surveys available metaphor corpora and discusses protocols for metaphor annotation. Any reader with an interest in metaphor, from beginning researchers to seasoned scholars, will find this book to be an invaluable guide to what is a fascinating linguistic phenomenon.\",\n" +
            "                \"venue\": \"Metaphor: A Computational Perspective\",\n" +
            "                \"year\": 2016,\n" +
            "                \"referenceCount\": 173,\n" +
            "                \"citationCount\": 28,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"1695574\",\n" +
            "                        \"name\": \"T. Veale\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2362276\",\n" +
            "                        \"name\": \"Ekaterina Shutova\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2817411\",\n" +
            "                        \"name\": \"Beata Beigman Klebanov\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"1514e10c81a12da4f201a4acc5e745a66a11ee86\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/1514e10c81a12da4f201a4acc5e745a66a11ee86\",\n" +
            "                \"title\": \"Proceedings of The 3rd Workshop on Multi-word Units in Machine Translation and Translation Technology (MUMTTT 2017)\",\n" +
            "                \"abstract\": \"Putting the Horses before the Cart: Identifying Multiword Expressions before Translation Translating multiword expressions (MWEs) is notoriously difficult. Part of the challenge stems from the analysis of non-compositional expressions in source texts, preventing literal translation. Therefore, before translating them, it is crucial to locate MWEs in the source text. We would be putting the cart before the horses if we tried to translate MWEs before ensuring that they are correctly identified in the source text. This paper discusses the current state of affairs in automatic MWE identification, covering rule-based methods and sequence taggers. While MWE identification is not a solved problem, significant advances have been made in the recent years. Hence, we can hope that MWE identification can be integrated into MT in the near future, thus avoiding clumsy translations that have often been mocked and used to motivate the urgent need for better MWE processing. iv Proceedings of The 3rd Workshop on Multi-word Units in Machine Translation and Translation Technology (MUMTTT 2017), London, 14 November 2017. Computational and Corpus-based Phraseology Recent advances and interdisciplinary approaches MUMTTT 2017 Academic Programme Tuesday, 14th November 2017 Session 1: Multi-words in Machine Translation Multi-word Adverbs How well are they handled in Parsing and Machine Translation? Martin Volk and Johannes Graën Chinese Separable Words in SMT Gongbo Tang and Fabienne Cap Out of the tailor or still in the woods? An empirical study of MWEs in MT Fabienne Cap Tuesday, 14th November 2017\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2015,\n" +
            "                \"referenceCount\": 63,\n" +
            "                \"citationCount\": 0,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"39222489\",\n" +
            "                        \"name\": \"J. Monti\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1804994\",\n" +
            "                        \"name\": \"G. C. Pastor\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3109039\",\n" +
            "                        \"name\": \"Violeta Seretan\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1746371\",\n" +
            "                        \"name\": \"R. Mitkov\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"beffa528ccb38bba8ec50f1d7ce69aba6428792b\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/beffa528ccb38bba8ec50f1d7ce69aba6428792b\",\n" +
            "                \"title\": \"Comprehensive Annotation of Multiword Expressions in a Social Web Corpus\",\n" +
            "                \"abstract\": \"Multiword expressions (MWEs) are quite frequent in languages such as English, but their diversity, the scarcity of individual MWE types, and contextual ambiguity have presented obstacles to corpus-based studies and NLP systems addressing them as a class. Here we advocate for a comprehensive annotation approach: proceeding sentence by sentence, our annotators manually group tokens into MWEs according to guidelines that cover a broad range of multiword phenomena. Under this scheme, we have fully annotated an English web corpus for multiword expressions, including those containing gaps.\",\n" +
            "                \"venue\": \"International Conference on Language Resources and Evaluation\",\n" +
            "                \"year\": 2014,\n" +
            "                \"referenceCount\": 35,\n" +
            "                \"citationCount\": 84,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"145254207\",\n" +
            "                        \"name\": \"Nathan Schneider\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2641566\",\n" +
            "                        \"name\": \"Spencer Onuffer\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2911298\",\n" +
            "                        \"name\": \"Nora Kazour\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2072361\",\n" +
            "                        \"name\": \"Emily Danchik\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"3337309\",\n" +
            "                        \"name\": \"Michael T. Mordowanec\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"40033562\",\n" +
            "                        \"name\": \"H. Conrad\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144365875\",\n" +
            "                        \"name\": \"Noah A. Smith\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"b8e54f8eb55bcd3f4ae762d7941c7d2b406298f2\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/b8e54f8eb55bcd3f4ae762d7941c7d2b406298f2\",\n" +
            "                \"title\": \"Discriminative Lexical Semantic Segmentation with Gaps: Running the MWE Gamut\",\n" +
            "                \"abstract\": \"We present a novel representation, evaluation measure, and supervised models for the task of identifying the multiword expressions (MWEs) in a sentence, resulting in a lexical semantic segmentation. Our approach generalizes a standard chunking representation to encode MWEs containing gaps, thereby enabling efficient sequence tagging algorithms for feature-rich discriminative models. Experiments on a new dataset of English web text offer the first linguistically-driven evaluation of MWE identification with truly heterogeneous expression types. Our statistical sequence model greatly outperforms a lookup-based segmentation procedure, achieving nearly 60% F1 for MWE identification.\",\n" +
            "                \"venue\": \"International Conference on Topology, Algebra and Categories in Logic\",\n" +
            "                \"year\": 2014,\n" +
            "                \"referenceCount\": 77,\n" +
            "                \"citationCount\": 105,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"145254207\",\n" +
            "                        \"name\": \"Nathan Schneider\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2072361\",\n" +
            "                        \"name\": \"Emily Danchik\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"1745899\",\n" +
            "                        \"name\": \"Chris Dyer\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"144365875\",\n" +
            "                        \"name\": \"Noah A. Smith\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"56fdd2e4a48e57f5696f807803db79b78e960274\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/56fdd2e4a48e57f5696f807803db79b78e960274\",\n" +
            "                \"title\": \"VPCTagger: Detecting Verb-Particle Constructions With Syntax-Based Methods\",\n" +
            "                \"abstract\": \"Verb-particle combinations (VPCs) con- sist of a verbal and a preposition/particle component, which often have some addi- tional meaning compared to the meaning of their parts. If a data-driven morpholog- ical parser or a syntactic parser is trained on a dataset annotated with extra informa- tion for VPCs, they will be able to iden- tify VPCs in raw texts. In this paper, we examine how syntactic parsers perform on this task and we introduce VPCTag- ger, a machine learning-based tool that is able to identify English VPCs in context. Our method consists of two steps: it first selects VPC candidates on the basis of syntactic information and then selects gen- uine VPCs among them by exploiting new features like semantic and contextual ones. Based on our results, we see that VPC- Tagger outperforms state-of-the-art meth- ods in the VPC detection task.\",\n" +
            "                \"venue\": \"MWE@EACL\",\n" +
            "                \"year\": 2014,\n" +
            "                \"referenceCount\": 22,\n" +
            "                \"citationCount\": 20,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2706166\",\n" +
            "                        \"name\": \"T. IstvánNagy\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"authorId\": \"2536091\",\n" +
            "                        \"name\": \"V. Vincze\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"7792812ccd421fef90b1dfe165a735a9a56829db\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/7792812ccd421fef90b1dfe165a735a9a56829db\",\n" +
            "                \"title\": \"English Complex Verb Constructions: Identification and Inference.\",\n" +
            "                \"abstract\": null,\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2012,\n" +
            "                \"referenceCount\": 118,\n" +
            "                \"citationCount\": 5,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"2054287479\",\n" +
            "                        \"name\": \"Y. Tu\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"citingPaper\": {\n" +
            "                \"paperId\": \"28e77994701fe2552c7cab3b962586abb6892a17\",\n" +
            "                \"url\": \"https://www.semanticscholar.org/paper/28e77994701fe2552c7cab3b962586abb6892a17\",\n" +
            "                \"title\": \"Lexical Semantic Analysis in Natural Language\",\n" +
            "                \"abstract\": \"This thesis concerns the lexical semantics of natural language text, studying from a computational perspective how words in sentences ought to be analyzed, how this analysis can be automated, and to what extent such analysis matters to other natural language processing (NLP) problems. It may not be obvious that words of text should be analyzed at all. After all, superficial uses of word tokens—most famously, bag-of-words representations and n-grams—are quite successful in settings ranging from information retrieval to language modeling. On the other hand, it is clear that there is a fuzzy relationship between the use of a word and the intended meaning, even when orthographic and morphological normalization (such as lemmatization or stemming) are applied. The word lexicon and its derivatives offer a good case in point:\",\n" +
            "                \"venue\": \"\",\n" +
            "                \"year\": 2012,\n" +
            "                \"referenceCount\": 154,\n" +
            "                \"citationCount\": 2,\n" +
            "                \"authors\": [\n" +
            "                    {\n" +
            "                        \"authorId\": \"145254207\",\n" +
            "                        \"name\": \"Nathan Schneider\"\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    String jsonRecommendations = "{\"recommendedPapers\": [{\"paperId\": \"16369ae79ac623b38ac904d3e7567f084318588f\", \"url\": \"https://www.semanticscholar.org/paper/16369ae79ac623b38ac904d3e7567f084318588f\", \"title\": \"A Simple and Efficient Algorithm for Finding Minimum Spanning Tree Replacement Edges\", \"abstract\": \". Given an undirected, weighted graph, the minimum spanning tree (MST) is a tree that connects all of the vertices of the graph with minimum sum of edge weights. In real world applications, network designers often seek to quickly find a replacement edge for each edge in the MST. For example, when a traffic accident closes a road in a transportation network, or a line goes down in a communication network, the replacement edge may reconnect the MST at lowest cost. In the paper, we consider the case of finding the lowest cost replacement edge for each edge of the MST. A previous algorithm by Tarjan takes O( m\\u03b1 ( m, n )) time and space, where \\u03b1 ( m, n ) is the inverse Ackermann\\u2019s function. Given the MST and sorted non-tree edges, our algorithm is the first practical algorithm that runs in O( m + n ) time and O( m + n ) space to find all replacement edges. Additionally, since the most vital edge is the tree edge whose removal causes the highest cost, our algorithm finds it in linear time.\", \"venue\": \"\", \"year\": 2022, \"referenceCount\": 22, \"citationCount\": 0, \"authors\": [{\"authorId\": \"153595774\", \"name\": \"D. Bader\"}, {\"authorId\": \"3422834\", \"name\": \"Paul Burkhardt\"}]}, {\"paperId\": \"2cd83fd263a4b9a5231912732b6d4598f2e1d77a\", \"url\": \"https://www.semanticscholar.org/paper/2cd83fd263a4b9a5231912732b6d4598f2e1d77a\", \"title\": \"Exploring structural properties of $k$-trees and block graphs\", \"abstract\": \"We present a new characterization of k -trees based on their reduced clique graphs and ( k + 1) -line graphs, which are block graphs. We explore structural properties of these two classes, showing that the number of clique-trees of a k -tree G equals the number of spanning trees of the ( k + 1) -line graph of G . This relationship allows to present a new approach for determining the number of spanning trees of any connected block graph. We show that these results can be accomplished in linear time complexity.\", \"venue\": \"\", \"year\": 2023, \"referenceCount\": 14, \"citationCount\": 0, \"authors\": [{\"authorId\": \"2759784\", \"name\": \"L. Markenzon\"}, {\"authorId\": \"1708244767\", \"name\": \"A. S. Oliveira\"}, {\"authorId\": \"2580268\", \"name\": \"Cybele T. M. Vinagre\"}]}, {\"paperId\": \"cab62cd2956a0c262b0c528c7d9315abe1c852ad\", \"url\": \"https://www.semanticscholar.org/paper/cab62cd2956a0c262b0c528c7d9315abe1c852ad\", \"title\": \"Restriction on minimum degree in the contractible sets problem\", \"abstract\": \"Let G be a 3 -connected graph. A set W \\u2282 V ( G ) is contractible if G ( W ) is connected and G \\u2212 W is a 2 -connected graph. In 1994, McCuaig and Ota formulated the conjecture that, for any k \\u2208 N , there exists m \\u2208 N such that any 3-connected graph G with v ( G ) (cid:62) m has a k -vertex contractible set. In this paper we prove that, for any k (cid:62) 5 , the assertion of the conjecture holds if \\u03b4 ( G ) (cid:62) (cid:2) 2 k +13 (cid:3) + 2 .\", \"venue\": \"\", \"year\": 2022, \"referenceCount\": 7, \"citationCount\": 0, \"authors\": [{\"authorId\": \"2160471476\", \"name\": \"Nikolai Karol\"}]}, {\"paperId\": \"8ca445edaa427b60e0c8f7382895df98062c70bb\", \"url\": \"https://www.semanticscholar.org/paper/8ca445edaa427b60e0c8f7382895df98062c70bb\", \"title\": \"RECTILINEAR STEINER ARBORESCENCE\", \"abstract\": \". Given a set of points in the \\ufb01rst quadrant, a rectilinear Steiner arborescence (RSA) is a directed tree rooted at the origin, containing all points, and composed solely of horizontal and vertical edges oriented from left to right, or from bottom to top. The complexity of \\ufb01nding an RSA with the minimum total edge length for general planar point sets has been a well-known open problem in algorithm design and VLSI routing. In this paper, we prove the problem is NP-complete in the strong sense.\", \"venue\": \"\", \"year\": null, \"referenceCount\": 0, \"citationCount\": 2, \"authors\": [{\"authorId\": \"2214730\", \"name\": \"Yiu Yu Ho\"}]}, {\"paperId\": \"062b7f779cec15247e14b0780b185c312aadad24\", \"url\": \"https://www.semanticscholar.org/paper/062b7f779cec15247e14b0780b185c312aadad24\", \"title\": \"Provably Fast and Space-Efficient Parallel Biconnectivity\", \"abstract\": \"Biconnectivity is one of the most fundamental graph problems. The canonical parallel biconnectivity algorithm is the Tarjan-Vishkin algorithm, which hasO (n +m) optimal work (number of operations) and polylogarithmic span (longest dependent operations) on a graph with n vertices andm edges. However, Tarjan-Vishkin is not widely used in practice. We believe the reason is the space-inefficiency (it generates an auxiliary graph with O (m) edges). In practice, existing parallel implementations are based on breath-first search (BFS). Since BFS has span proportional to the diameter of the graph, existing parallel BCC implementations suffer from poor performance on large-diameter graphs and can be even slower than the sequential algorithm on many real-world graphs. We propose the first parallel biconnectivity algorithm (FAST-BCC) that has optimal work, polylogarithmic span, and is space-efficient. Our algorithm first generates a skeleton graph based on any spanning tree of the input graph. Then we use the connectivity information of the skeleton to compute the biconnectivity of the original input. All the steps in our algorithm are highly-parallel. We carefully analyze the correctness of our algorithm, which is highly non-trivial. We implemented FAST-BCC and compared it with existing implementations, including GBBS, Slota and Madduri\\u2019s algorithm, and the sequential Hopcroft-Tarjan algorithm. We ran them on a 96-core machine on 27 graphs, including social, web, road, k-NN, and synthetic graphs, with significantly varying sizes and edge distributions. FAST-BCC is the fastest on all 27 graphs. On average (geometric means), FAST-BCC is 5.1\\u00d7 faster than GBBS, and 3.1\\u00d7 faster than the best existing baseline on each graph. CCSConcepts: \\u2022Theory of computation\\u2192 Sharedmemory algorithms;Graph algorithms analysis;Parallel algorithms.\", \"venue\": \"ArXiv\", \"year\": 2023, \"referenceCount\": 68, \"citationCount\": 0, \"authors\": [{\"authorId\": \"2118186408\", \"name\": \"Xiaojun Dong\"}, {\"authorId\": \"2108528004\", \"name\": \"Letong Wang\"}, {\"authorId\": \"46964402\", \"name\": \"Yan Gu\"}, {\"authorId\": \"2108541101\", \"name\": \"Yihan Sun\"}]}, {\"paperId\": \"d9471e3eb8b7ccd429fa8de413266d84f6f9d041\", \"url\": \"https://www.semanticscholar.org/paper/d9471e3eb8b7ccd429fa8de413266d84f6f9d041\", \"title\": \"Parallel Connectivity Algorithms\", \"abstract\": \"We propose and implement two parallel algorithms to test the connectivity and find the connected components of a network in parallel. In both cases, the connectivity matrix of the graph is partitioned to p processors. The first parallel algorithm (Alg. 2) processors test connectivity in their partitions and then cooperate to decide. The second parallel algorithm (Alg. 4) forms a labelled connectivity matrix and then partitions this matrix to processors to find the components of a disconnected graph. We show both algorithms achieve significant speedups even with only few processors.\", \"venue\": \"2022 3rd International Informatics and Software Engineering Conference (IISEC)\", \"year\": 2022, \"referenceCount\": 5, \"citationCount\": 0, \"authors\": [{\"authorId\": \"1691511\", \"name\": \"K. Erciyes\"}, {\"authorId\": \"2198761892\", \"name\": \"Beh\\u00e7et Melih Sar\\u0131bat\\u0131r\"}]}, {\"paperId\": \"8a2177c38121e9b3694a6462d9cf65bddba33b7e\", \"url\": \"https://www.semanticscholar.org/paper/8a2177c38121e9b3694a6462d9cf65bddba33b7e\", \"title\": \"A Polynomial-Time Algorithm for MCS Partial Search Order on Chordal Graphs\", \"abstract\": \"We study the partial search order problem (PSOP) proposed recently by Schef\\ufb02er [WG 2022]. Given a graph G together with a partial order over the vertices of G , this problem determines if there is an S -ordering that is consistent with the given partial order, where S is a graph search paradigm like BFS, DFS, etc. This problem naturally generalizes the end-vertex problem which has received much attention over the past few years. It also generalizes the so-called F -tree recognition problem which has just been studied in the literature recently. Our main contribution is a polynomial-time dynamic programming algorithm for the PSOP on chordal graphs with respect to the maximum cardinality search (MCS). This resolves one of the most intriguing open questions left in the work of Shef\\ufb02er [WG 2022]. To obtain our result, we propose the notion of layer structure and study numerous related structural properties which might be of independent interest.\", \"venue\": \"ArXiv\", \"year\": 2022, \"referenceCount\": 32, \"citationCount\": 0, \"authors\": [{\"authorId\": \"1382860934\", \"name\": \"Guozhen Rong\"}, {\"authorId\": \"1784927883\", \"name\": \"Yongjie Yang\"}, {\"authorId\": \"2108953789\", \"name\": \"Wenjun Li\"}]}, {\"paperId\": \"d382ac12e7b2ea9f6fd961fc0df1285d18307684\", \"url\": \"https://www.semanticscholar.org/paper/d382ac12e7b2ea9f6fd961fc0df1285d18307684\", \"title\": \"On the strong metric dimension of composed graphs\", \"abstract\": \"Two vertices u and v of an undirected graph G are strongly resolved by a vertex w if there is a shortest path between w and u containing v or a shortest path between w and v containing u . A vertex set R is a strong resolving set for G if for each pair of vertices there is a vertex in R that strongly resolves them. The strong metric dimension of G is the size of a minimum strong resolving set for G . We show that a minimum strong resolving set for an undirected graph G can be computed e\\ufb03ciently if and only if a minimum strong resolving set for each biconnected component of G can be computed e\\ufb03ciently.\", \"venue\": \"ArXiv\", \"year\": 2022, \"referenceCount\": 29, \"citationCount\": 0, \"authors\": [{\"authorId\": \"2087643607\", \"name\": \"M. Wagner\"}, {\"authorId\": \"2047402952\", \"name\": \"Yannick Schmitz\"}, {\"authorId\": \"34919343\", \"name\": \"Egon Wanke\"}]}, {\"paperId\": \"4b64934894a9f84f371f87e39e1da9d70ce1de97\", \"url\": \"https://www.semanticscholar.org/paper/4b64934894a9f84f371f87e39e1da9d70ce1de97\", \"title\": \"On the rainbow planar Tur\\\\'an number of paths\", \"abstract\": \"An edge-colored graph is said to contain a rainbow- F if it contains F as a subgraph and every edge of F is a distinct color. The problem of maximizing edges among n -vertex properly edge-colored graphs not containing a rainbow- F , known as the rainbow Tur\\u00b4an problem, was initiated by Keevash, Mubayi, Sudakov and Verstra\\u00a8ete. We investigate a variation of this problem with the additional restriction that the graph is planar, and we denote the corresponding extremal number by ex \\u2217P ( n, F ). In particular, we determine ex \\u2217P ( n, P 5 ), where P 5 denotes the 5-vertex path.\", \"venue\": \"\", \"year\": 2023, \"referenceCount\": 11, \"citationCount\": 0, \"authors\": [{\"authorId\": \"102332665\", \"name\": \"Ervin GyHori\"}, {\"authorId\": \"145420400\", \"name\": \"Ryan R. Martin\"}, {\"authorId\": \"1387991114\", \"name\": \"Addisu Paulos\"}, {\"authorId\": \"34834046\", \"name\": \"C. Tompkins\"}, {\"authorId\": \"15266738\", \"name\": \"Kitti Varga\"}]}, {\"paperId\": \"125a12684d2aadf9666d5f439f34b32cf7b1dc9f\", \"url\": \"https://www.semanticscholar.org/paper/125a12684d2aadf9666d5f439f34b32cf7b1dc9f\", \"title\": \"Parallel Minimum Cuts in O(m log2 n) Work and Low Depth\", \"abstract\": \"We present a randomized O(m log2 n) work, O(polylog n) depth parallel algorithm for minimum cut. This algorithm matches the work bounds of a recent sequential algorithm by Gawrychowski, Mozes, and Weimann [ICALP\\u201920], and improves on the previously best parallel algorithm by Geissmann and Gianinazzi [SPAA\\u201918], which performs O(m log4 n) work in O(polylog n) depth. Our algorithm makes use of three components that might be of independent interest. Firstly, we design a parallel data structure that efficiently supports batched mixed queries and updates on trees. It generalizes and improves the work bounds of a previous data structure of Geissmann and Gianinazzi and is work efficient with respect to the best sequential algorithm. Secondly, we design a parallel algorithm for approximate minimum cut that improves on previous results by Karger and Motwani. We use this algorithm to give a work-efficient procedure to produce a tree packing, as in Karger\\u2019s sequential algorithm for minimum cuts. Lastly, we design an efficient parallel algorithm for solving the minimum 2-respecting cut problem.\", \"venue\": \"ACM Transactions on Parallel Computing\", \"year\": 2022, \"referenceCount\": 36, \"citationCount\": 0, \"authors\": [{\"authorId\": \"2116530848\", \"name\": \"Daniel Anderson\"}, {\"authorId\": \"1717462\", \"name\": \"G. Blelloch\"}]}]}";

    @Test
    void convertRecommendations() throws JsonProcessingException, JSONException {

        // execution
        List<ApiPaper> apiPapers = new SemanticScholarWrapper().convertRecommendations(jsonRecommendations);

        // test
        System.out.println(apiPapers.size());
        assertNotNull(apiPapers);

        for (ApiPaper paper : apiPapers) {
            assertNotNull(paper.getTitle());
            assertNotNull(paper.getId());
        }
    }

    @Test
    void convertCitationsReferences() throws JSONException, JsonProcessingException {

        // test references
        List<ApiPaper> references = new SemanticScholarWrapper().convertCitationsReferencesSearch(jsonReferences, "citedPaper");

        assertNotNull(references);

        for (ApiPaper paper : references) {
            assertNotNull(paper.getTitle());
            assertNotNull(paper.getId());
        }

        // test citations
        List<ApiPaper> citations = new SemanticScholarWrapper().convertCitationsReferencesSearch(jsonCitations, "citingPaper");

        assertNotNull(citations);

        for (ApiPaper paper : citations) {
            assertNotNull(paper.getTitle());
            assertNotNull(paper.getId());
        }
    }

    @Test
    void convertToPaper() throws JSONException, JsonProcessingException {
        List<ApiPaper> paper = new SemanticScholarWrapper().convertToPaper(jsonPaper);
        for (int i = 0; i < paper.get(0).getAuthors().size(); i++) {
            assertNotNull(paper.get(0).getAuthors().get(i).getName());
            assertNotNull(paper.get(0).getAuthors().get(i).getId());
        }
        assertEquals(1, paper.size());
        assertNotNull(paper.get(0).getId());
        assertNotNull(paper.get(0).getTitle());
        assertNotNull(paper.get(0).getPdfUrl());
        assertNotNull(paper.get(0).getVenue());
        assertNotNull(paper.get(0).getAbstractText());

    }
}