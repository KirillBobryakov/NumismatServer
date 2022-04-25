package numismat;


import numismat.entity.pieces.Piece;
import numismat.entity.pieces.PieceRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MathService {

    private final PieceRepository pieceRepository;

    public MathService(PieceRepository pieceRepository) {
        this.pieceRepository = pieceRepository;
    }

    @PutMapping
    Mono<Piece> createOrUpdatePiece(@RequestBody Piece piece){
        return pieceRepository.save(piece);
    }

    @PutMapping("/pieces/{id}")
    public Piece replacePiece(@RequestBody Piece newPiece, @PathVariable Long id) {

        return pieceRepository.findById(id)
                .map(piece -> {
                    piece.setNumistaURL(newPiece.getNumistaURL());
                    return pieceRepository.save(piece);
                }).blockOptional()
                .orElseGet(() -> {
                    newPiece.setId(id);
                    return pieceRepository.save(newPiece);
                }).block();
    }

    @GetMapping("/")
    public Piece index(){
        Piece piece = new Piece();
        piece.setNumistaURL("https://en.numista.com/catalogue/pieces854.html");

        return createOrUpdatePiece(piece).block();
    }

    @PostMapping("/pieces")
    public Piece savePiece(@RequestBody Piece piece){
        return createOrUpdatePiece(piece).block();
    }

    @GetMapping("/pieces")
    List<Piece> all() {
        return pieceRepository.findAll().toStream().collect(Collectors.toList());
    }

//    @RequestMethod("GET")
//    @ResourcePath("sum")
//    public double getSum(double a, double b){
//        return a + b;
//    }
//
//    @RequestMethod("GET")
//    @ResourcePath("sum")
//    public double getSum(List<Double> values){
//        double total = 0;
//
//        for (double value : values) {
//            total += value;
//        }
//
//        return total;
//    }



}
