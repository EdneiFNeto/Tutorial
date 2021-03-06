

//=======================================================================
// ------------------- NODE JS, EXPRESS, MONGO DB -----------------------
//=======================================================================

//=======================================================================
//EXPRESS, NODE E MONGO
//=======================================================================
//criar diretorio
//=======================================================================
//npm init [GERA PACKEGE.JSON]
//=======================================================================
//instalar dependncias: express, mongoose, nodemon
//no packege.json
//=======================================================================
//criar diretorios: app, public, models, views, rotas
//=======================================================================
{
  "name": "restfullnode",
  "version": "1.0.0",
  "description": "",
  "main": "server.js",

  "scripts": {
    "dev":"nodemon"//change
  },

  "dependencies": {
    "body-parser": "~1.18.3",//add
    "express": "*",//add
    "mongoose": "~5.2.9",//add
    "nodemon": "^1.18.3",//add
    "request": "~2.88.0"//add
  },
  "author": "",
  "license": "ISC"
}

//=======================================================================
//instalar dependecias: npm install
//=======================================================================
//rodar nodemon: npm run dev
//=======================================================================
//mLAbs
//=======================================================================
username:edneifneto
pass:ednei1234567

//=======================================================================
//MODELS - PRODUTOS.JS
//=======================================================================
var mongoose 		= require('mongoose');
var Sheme 			= mongoose.Schema;

var ProdutoSheme 	= new Sheme({
    nome:String,
    preco:Number,
    descricao:String
});

module.exports = mongoose.model('Produto', ProdutoSheme);

//=======================================================================
// ------------------------------- FIM ----------------------------------
//=======================================================================


//=======================================================================
//ROUTES - PRODUTOS.JS
//=======================================================================

var router 			= require('express').Router();
var Produto 		= require('./../models/produto');

//A cada get ou post exobe essa menssage
router.use(function (req, res, next) {
    console.log("Notivficacao do produto....");
    next();//permite que aplicacao continue
});

//rotas produtos
//1 methodo [POST - Criar produto], Acessar : http://localhost:8000/api/produtos]
router.route('/').post(function (req, res) {
    var produto = new Produto();
    //seta os produtos
    produto.nome = req.body.nome;
    produto.preco = req.body.preco;
    produto.descricao = req.body.descricao;

    //salavar no banco
    produto.save(function (error) {
        if (error)
            res.send('Erro ao salvar produto: ' + error);
        res.json({ message: "Produto cadastrado com sucesso!" });
    })
});

//1 methodo [select produto, Acessar[GET] : http://localhost:8000/api/produtos]
router.get(function (req, res) {
    Produto.find(function (error, produtos) {
        if (error)
            res.send('Error ao selecionar produtos ' + error);
        res.json({ produtos });
    });
});

//2 methodo [SELECT produto POR id], 
//Acessar[GET] : http://localhost:8000/api/produtos/:produto_id]

router.route('/:produto_id').get(function (req, res) {

    Produto.findById(req.params.produto_id, function (error, produto) {
        if (error)
            res.send('Produto nao encontrado ' + error);
        res.json(produto);
    });
});

//3 methodo [UPDATE produto POR id], 
//Acessar[PUT] : http://localhost:8000/api/produtos/:produto_id]

router.route('/:produto_id').put(function (req, res) {
    //passo 1 - [pesqusar id do produto]
    Produto.findById(req.params.produto_id, function (error, produto) {
        if (error)
            res.send('Erro ao encontrar produto ' + error);

        //passo 2 - alterar dadosdo produto
        produto.nome = req.body.nome;
        produto.preco = req.body.preco;
        produto.descricao = req.body.descricao;

        //passo 3 - salvar os dados
        produto.save(function (error) {
            if (error)
                res.send('Erro ao atualizar ' + error);
            res.json({ message: 'Produto autlizado com sucesso!' });
        })
    });
});

//4 methodo [DELETE produto POR id], 
//Acessar[DELETE] : http://localhost:8000/api/produtos/:produto_id]
//ID: 5b78fd098f8369258c057cff
router.route('/:produto_id').delete(function (req, res) {
    Produto.remove({
        _id: req.params.produto_id
    }, function (error) {
        if (error)
            res.send('Produto nao enontrado ');
        res.json({ message: 'Produto excluido com sucesso' });
    });
});

//4 LIMOAR TABLEA
//http://localhost:8000/api/produtos

router.route('/').delete(function (req, res) {
    Produto.remove(function (error) {
        if (error)
            res.send('Erroa ao deletar os dados');
        res.json({ message: 'Todos os dados excluidos' })
    })
});


module.exports = router;

//=======================================================================
// ------------------------------- FIM ----------------------------------
//=======================================================================

//=======================================================================
//--------------------- ARQUIVO APP.JS ----------------------------------
//=======================================================================

var express 		= require('express');
var app 			= express();
var bodyParse 		= require('body-parser');

var mongoose 		= require('mongoose');

//INSTANCIA DAS CLASS
var ProdutoRoutes 	= require('./app/routes/produtos');
var RecorderRoutes 	= require('./app/routes/recorder');

mongoose.connect('mongodb://edneifneto:ednei1234567@ds125862.mlab.com:25862/nodecrudapi', {
    useNewUrlParser: true
});

app.use(bodyParse.urlencoded({ extended: true }));
app.use(bodyParse.json());

//DEFINICAO DA PORTA
var port = process.env.port || 8000;

//DEFINICAO DAAS ROTAS
app.use('/produtos', ProdutoRoutes);//DEFINE A URL:EXCEMPLO: http://localhost:8000/produtos
app.use('/recorder', RecorderRoutes);//DEFINE A URL:EXCEMPLO: http://localhost:8000/recorder

//rodar aplicacao
app.listen(port, function () {
    console.log('Rodando app na porta ' + port);
});

//=======================================================================
// ------------------------------- FIM ----------------------------------
//=======================================================================


//=======================================================================
//------------------- MODEL/RECORDER.JS ---------------------------------
//=======================================================================

var mongoose 		= require('mongoose');
var Sheme 			= mongoose.Schema;

var RecordeSheme 	= new Sheme({
    nome:String,
    preco:Number,
    descricao:String
});

module.exports 		= mongoose.model('Recorde', RecordeSheme);

//=======================================================================
// ------------------------------- FIM ----------------------------------
//=======================================================================

//=======================================================================
//--------------------- ROUTES/RECORDER.JS ------------------------------
//=======================================================================

var routes 			= require('express').Router();
var Recorder 		= require('./../models/recorder');

//NOTOFICA A CADA ALTERACAO NO BANCO
routes.use(function (req, res, next) {
    console.log("Notiificacao do recorder....");
    next();//permite que aplicacao continue
});

//post add novo produto
routes.post('/', function (req, res) {
    //verifcar se existe [post nome]
    if (!req.body.nome)
        return res.status(422).send({ error: 'Nao existe nome' });

    var recorder = new Recorder({
        nome: req.body.nome,
        preco: req.body.preco,
        descricao: req.body.descricao,
    });

    recorder.save(function (error) {
        if (error)
            return res.status(403).send({ error: error });
        return res.send({ message: 'produto cadastrado com sucesso' });
    });
});

//get select produtos
routes.get('/', function (req, res) {
    Recorder.find(function (error, recorder) {
        if (error)
            res.send('Error ao selecionar produtos ' + error);
        res.send({ recorder });
    });
});

routes.route('/:id').get(function (req, res) {

    Recorder.findById(req.params.id, function (error, recorder) {
        if (error)
            res.send('Recorder nao encontrado ' + error);
        res.json({recorder});
    });
});

//put atualizar produtos
routes.put('/:id', function (req, res) {
    if (!req.body.id)
        return res.status(422).send({ error: '[PUT] Nao existe id' });
    Recorder.findOne({
        nome: res.body.nome,
        preco: res.body.preco,
        descricao: res.body.descricao,
    }).exec(function (error, recorder) {
        if (error)
            return res.status(403).send({ error: error });
        recorder.save(function (error, recorder) {
            if (error)
                return res.status(403).send({ error: error });
            return res.send({ message: 'produto atualizado com sucesso' });
        })
    });
});

//passo 1 - [pesqusar id do produto]
routes.route('/:id').put(function (req, res) {
    Recorder.findById(req.params.findById, function (error, recorder) {
        if (error)
            res.send('Erro ao encontrar produto ' + error);

        //passo 2 - alterar dadosdo produto
        recorder.nome = req.body.nome;
        recorder.preco = req.body.preco;
        recorder.descricao = req.body.descricao;

        //passo 3 - salvar os dados
        recorder.save(function (error) {
            if (error)
                res.status(403).send('Erro ao atualizar ' + error);
            res.json({ message: 'recorder autlizado com sucesso!' });
        });
    });
});

module.exports = routes;

//=======================================================================
// ------------------------------- FIM ----------------------------------
//=======================================================================



//=======================================================================
// ------------ PRODUTO USANDO CONTROLLER -------------------------------
//=======================================================================
//IMPLEMENTA OS METHODOS DA CLASS CONTROLLER
//=======================================================================

var router = require('express').Router();
var Produto = require('./../models/produto');

//IMPORT CLASS CONTRLLER
var ctrProduto = require('./../controller/produtoController');

//A cada get ou post exobe essa menssage
router.use(function (req, res, next) {
    console.log("Notivficacao do produto....");
    next();//permite que aplicacao continue
});

//1 methodo [INSERT], 
//Acessar[POST] : http://localhost:8000/api/produtos/:id]
router.route('/').post(ctrProduto.ProdutoControllerSalvar);

//2 methodo [SELECT], 
//Acessar[GET] : http://localhost:8000/api/produtos/:id]
router.route('/').get(ctrProduto.ProdutoControllerSelect);

//3 methodo [SELECT produto POR id], 
//Acessar[GET] : http://localhost:8000/api/produtos/:id]
router.route('/:id').get(ctrProduto.ProdutoControllerSelectPorId);

//4 methodo [UPDATE produto POR id], 
//Acessar[PUT] : http://localhost:8000/api/produtos/:produto_id]
router.route('/:id').put(ctrProduto.ProdutoControllerUpdate);

//5 methodo [DELETE produto POR id], 
//Acessar[DELETE] : http://localhost:8000/api/produtos/:produto_id]
//ID: 5b78fd098f8369258c057cff
router.route('/:id').delete(ctrProduto.ProdutoControllerDelete);

//6 LIMOAR TABLEA
//http://localhost:8000/api/produtos
router.route('/').delete(ctrProduto.ProdutoControllerDeleteAll);

module.exports = router;

//=======================================================================
// ------------------------------- FIM ----------------------------------
//=======================================================================


//=======================================================================
// ------------- CONTRLLER/PRODUTOCONTROLER.JS --------------------------
//=======================================================================
//REPONSALVEL PARA CRIAR SO METHODOS :INSERT, UPDATE, DELELE DA CLASS PRODUTO
//=======================================================================

var Produto = require('./../models/produto');

module.exports.ProdutoControllerSalvar = function (req, res) {

    var produto = new Produto();
    //seta os produtos
    produto.nome = req.body.nome;
    produto.preco = req.body.preco;
    produto.descricao = req.body.descricao;

    //salavar no banco
    produto.save(function (error) {
        if (error)
            res.send('Erro ao salvar produto: ' + error);
        res.json({ message: "Produto cadastrado com sucesso!" });
    })
}

module.exports.ProdutoControllerSelect = function (req, res) {

    Produto.find(function (error, produtos) {
        if (error)
            res.send('Error ao selecionar produtos ' + error);
        res.json({ produtos });
    });
}

module.exports.ProdutoControllerSelectPorId = function (req, res) {

    Produto.findById(req.params.id, function (error, produto) {
        if (error)
            res.send('Produto nao encontrado ' + error);
        res.json(produto);
    });
}

module.exports.ProdutoControllerUpdate = function (req, res) {

    Produto.findById(req.params.id, function (error, produto) {
        if (error)
            res.send('Erro ao encontrar produto ' + error);

        //passo 2 - alterar dadosdo produto
        produto.nome = req.body.nome;
        produto.preco = req.body.preco;
        produto.descricao = req.body.descricao;

        //passo 3 - salvar os dados
        produto.save(function (error) {
            if (error)
                res.send('Erro ao atualizar ' + error);
            res.json({ message: 'Produto autlizado com sucesso!' });
        })
    });
}

module.exports.ProdutoControllerDelete = function (req, res) {

    Produto.remove({
        _id: req.params.id
    }, function (error) {
        if (error)
            res.send('Produto nao enontrado ');
        res.json({ message: 'Produto excluido com sucesso' });
    });
}

module.exports.ProdutoControllerDeleteAll = function (req, res) {
    Produto.remove(function (error) {
        if (error)
            res.send('Erroa ao deletar os dados');
        res.json({ message: 'Todos os dados excluidos' })
    })
 }

//=======================================================================
// ------------------------------- FIM ----------------------------------
//=======================================================================
