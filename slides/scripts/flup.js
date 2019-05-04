const chokidar = require('chokidar');

// One-liner for current directory, ignores .dotfiles
chokidar.watch('slides/*.adoc',).on('all', (event, path) => {
  console.log(event, path);
});
