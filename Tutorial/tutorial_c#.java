
//============================================================
//TROCA DE VALORES
//============================================================
//PRPRIEDADES DO FORM
//============================================================

/*
	FormBorderStyle = FixedSingle, Fixed3D, FixedDialog, Sizable, FixedToolWindow
	e SizableToolWindow.

	A Sizable, que é o estilo padrão, permite que o usuário altere o tamanho
	da janela durante sua execução. 

	Já a FixedSingle não permite essa
	mudança, ou seja, a janela terá as dimensões atribuídas em tempo de desenho.

	A propriedade MaximizeBox é responsável por definir se o botão de
	maximização de uma janela ( Maximize), exibido em conjunto com outros
	dois botões ( Minimize e Close) do lado direito da barra de título, será
	mostrado ou não.

	A propriedade MinimizeBox é responsável por definir se o botão de
	minimização de uma janela ( Minimize), exibido em conjunto com outros
	dois botões ( Maximize e Close) do lado direito da barra de título, será
	mostrado ou não.

	StartPosition = CenterScreen
	A propriedade StartPosition é responsável por definir a posição
	inicial da janela quando for exibida na primeira vez. Além do valor a
	ser atribuído, existem as opções: Manual, WindowsDefaultLocation,
	WindowsDefaultBounds e CenterParent.
*/
//============================================================
//TEXBOX
//============================================================

/*
A propriedade ReadOnly é responsável por definir se o controle deverá
ou não receber o foco durante a navegação entre os controles por meio do
pressionamento da tecla Tab.

A propriedade ForeColor é responsável pela definição da cor em que
o texto será exibido no controle.

*/



//============================================================
//NAMESAPCE
//============================================================
/*
	Um Namespace éumrecurso que permite organizar logicamente as classes,
criando uma espécie de estrutura em árvore, como se fossem diretórios
ou pastas. Dessa maneira, toda classe possui seu nome de maneira qualificada,
ou seja, ele completo. No caso da classe Form1, seu nome qualificado
é TrocaDeValores.Form1.
*/
//============================================================
//COMPILAR CTRL+F5
//============================================================

//============================================================
//MESSAGEBOX
//============================================================

MessageBox.Show("Todos ops dados precosam ser solicitados", "Atencao",
	MessageBoxButtons.OK, MessageBoxIcon.Information);

//============================================================
//DATA
//============================================================
lblHoje.Text = "Hoje e " + DateTime.Now.ToShortDateString();
//inseir calendario
DateTimePicker

//============================================================
//EVENT ENTER [EVENTO OCORRE QAUNDO PRECIONA DENTRO DO TEXTBOX]
//============================================================

private void textAnoUltimoAniversario_Enter(object sender, EventArgs e)
{
	if(textAnoNascimento.Text.Trim().Length<4)
	{
		MessageBox.Show("E preciso informar o Ano de Nascimento", "Anteção",
		MessageBoxButtons.OK, MessageBoxIcon.Information);
		textAnoNascimento.Focus();
	}
}
