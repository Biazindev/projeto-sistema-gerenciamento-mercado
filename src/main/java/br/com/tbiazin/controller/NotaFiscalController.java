package br.com.tbiazin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tbiazin.daoimpl.EmitirNotaFiscalRequestDaoImpl;
import br.com.tbiazin.domain.NotaFiscal;
import br.com.tbiazin.service.IntegracaoSefazService;
import br.com.tbiazin.service.interfaces.INotaFiscalService;

@RestController
@RequestMapping("/api/notas-fiscais")
public class NotaFiscalController {

    @Autowired
    private INotaFiscalService notaFiscalService;

    @Autowired
    private IntegracaoSefazService integracaoSefazService;

    @PostMapping("/emitir")
    public ResponseEntity<NotaFiscal> emitirNotaFiscal(
            @RequestBody EmitirNotaFiscalRequestDaoImpl request,
            @RequestHeader("Authorization") String token) {

        NotaFiscal notaFiscal = notaFiscalService.emitirNotaFiscal(
                request.getClienteId(), 
                request.getProdutoIds(), 
                request.getQuantidades(), 
                token.replace("Bearer ", "eyJ0eXAiOiJKV1QiLCJraWQiOiIwMWIwNDFkMWQ2MTU0NjA0NzNkMWI1NGFhOGRlNGQ1NyIsImFsZyI6IlJTMjU2In0.eyJzY29wZSI6Im5mZSBjZXAgY25waiBuZnNlIiwianRpIjoiYjk4MjlhMjUtMTNhNC00YjU"
                		+ "4LWFmM2MtYjFkZDY4MDc5NmNhIiwiaHR0cHM6XC9cL251dmVtZmlzY2FsLmNvbS5iclwvdGVuYW50X2lkIjoiM2ExM2ZmMTYtYmRmYy00YTVkLWI2M2YtMDg3MzAyYzM5YjFmIiwiaXNzIjoiaHR0cHM6XC9cL2F1dGgubnV2ZW1maXNjYWwuY29tLmJyIi"
                		+ "wiYXVkIjoiaHR0cHM6XC9cL2FwaS5zYW5kYm94Lm51dmVtZmlzY2FsLmNvbS5iclwvIiwiZXhwIjoxNzI0OTg1Mjg0LCJpYXQiOjE3MjIzOTMyODQsImNsaWVudF9pZCI6IjVGYnRPc1p6a2RUWk90Zm0xenY5In0.gqZyhm2sWG77ThteDH5N9vlIvoO2zW"
                		+ "Ys3C_bZlXeXA3pHccwoOCUTw1u6iyYqyzOIk5Ej2mM2pa2ffkJMHihhmHkDOPkexyK16_PIH_Ru44oae71OxxNrjocY7or3c5hGlEF10VjGoqcW5XoTra4jsMVrO3GMacInX41BsscJ_"
                		+ "zjH5wvTaaNFHiEBBXFK89hd9tkYlR0vjsxglIQM3XMUJrHhP8LRA6hR-K5lOQtOnIHE7a868KU9hXRmmE8OEYw8Yy0VHC7dlmpwmDXax9P2dRkNd0nnLwWtlLytodf2ZO-KGfhzJueZDHj-AqCaJPDhTjG_yTeufE8LY8ASTAQVw"));
        return ResponseEntity.ok(notaFiscal);
    }

    @PostMapping("/enviar/{id}")
    public ResponseEntity<NotaFiscal> enviarNotaFiscal(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        NotaFiscal notaFiscal = notaFiscalService.buscarNotaFiscalPorId(id);
        integracaoSefazService.enviarNotaFiscal(notaFiscal, token.replace("Bearer ", ""));
        return ResponseEntity.ok(notaFiscal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscal> buscarNotaFiscalPorId(@PathVariable Long id) {
        NotaFiscal notaFiscal = notaFiscalService.buscarNotaFiscalPorId(id);
        return ResponseEntity.ok(notaFiscal);
    }

    @GetMapping
    public ResponseEntity<List<NotaFiscal>> listarNotasFiscais() {
        List<NotaFiscal> notasFiscais = notaFiscalService.listarNotasFiscais();
        return ResponseEntity.ok(notasFiscais);
    }
}
