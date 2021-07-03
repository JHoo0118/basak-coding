// Initial data
const HTML_CODE = `<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
</head>
<body>
  <div class="alert alert-warning" role="alert">
    Bootstrap 4 CSS injected!
  </div>
</body>
</html>`;

const CSS_LINKS = [
  `https://cdn.jsdelivr.net/npm/bootstrap/dist/css/bootstrap.min.css`,
];

// Elements
const editorCode = document.getElementById('editorCode');
// const editorPreview = document.getElementById('editorPreview').contentWindow.document;
// const editorCopyButton = document.getElementById('editorCopyClipboard');

// <iframe> inject CSS
CSS_LINKS.forEach((linkURL) => {
  const link = document.createElement('link');
  link.href = linkURL;
  link.rel = 'stylesheet';
  // editorPreview.head.appendChild(link);
});

// Monaco loader
require.config({
  paths: {
    vs: 'https://cdn.jsdelivr.net/npm/monaco-editor/min/vs',
  },
});

window.MonacoEnvironment = {
  getWorkerUrl: function (workerId, label) {
    return `data:text/javascript;charset=utf-8,${encodeURIComponent(`
        self.MonacoEnvironment = {
          baseUrl: 'https://cdn.jsdelivr.net/npm/monaco-editor/min/'
        };
        importScripts('https://cdn.jsdelivr.net/npm/monaco-editor/min/vs/base/worker/workerMain.js');`)}`;
  },
};

// Monaco init
require(['vs/editor/editor.main'], function () {
  createEditor(editorCode, HTML_CODE);
});

function createEditor(editorContainer, code) {
	fetch('/js/monacoTheme.json')
    .then(data => data.json())
    .then(data => {
	    monaco.editor.defineTheme('monokai', data);
	    monaco.editor.setTheme('monokai');
	})
  // monaco.editor.defineTheme('myTheme', myTheme)
  let editor = monaco.editor.create(editorContainer, {
    value: code,
    language: 'html',
    minimap: {
      enabled: false,
    },
    theme:'myTheme',
    automaticLayout: true,
    roundedSelection: true,
    contextmenu: false,
    scrollBeyondLastLine: false,
    fontSize: 17,
    scrollbar: {
      useShadows: false,
      vertical: 'visible',
      horizontal: 'visible',
      horizontalScrollbarSize: 18,
      verticalScrollbarSize: 18,
    },
  });

  // editorPreview.body.innerHTML = HTML_CODE;

  editor.onDidChangeModelContent(() => {
    // editorPreview.body.innerHTML = editor.getValue();
  });

/*
  editorCopyButton.onclick = () => {
    copyToClipboard(editor.getValue());
  };
*/
}

function copyToClipboard(str) {
  const el = document.createElement('textarea');
  el.value = str;
  document.body.appendChild(el);
  el.select();
  document.execCommand('copy');
  document.body.removeChild(el);
}
