const asciidoctor = require('asciidoctor.js')();
require('asciidoctor-reveal.js');
const fs = require('fs-extra');
const {resolve} = require('path');

const base_dir = resolve(__dirname + "/..");
const out_dir = base_dir + '/out';

// Sets additional document attributes, which override equivalently-named
// attributes defined in the document unless the value ends with @
const own = {
    'slides': 'slides'
};

const Transition = Object.freeze({
    NONE: 'none',
    FADE: 'fade',
    SLIDE: 'slide',
    CONVEX: 'convex',
    CONCAVE: 'concave',
    ZOOM: 'zoom'
});

const TransitionSpeed = Object.freeze({
    SLOW: 'slow',
    DEFAULT: 'default',
    FAST: 'fast'
});

const Theme = Object.freeze({
  BLACK: 'black',
  WHITE: 'white',
  BEIGE: 'beige',
  SIMPLE: 'simple',
  MOON: 'moon',
  NIGHT: 'night',
  LEAGUE: 'league',
  SERIF: 'serif',
  SKY: 'sky',
  SOLARIZED: 'solarized'
});

// https://asciidoctor.org/docs/asciidoctor-revealjs/#reveal-js-options
const asciidoctor_reveal_js = {
    'revealjsdir': base_dir + '/node_modules/reveal.js@',
    'revealjs_history': true,
    'revealjs_theme': Theme.BEIGE,
    'revealjs_transition': Transition.DEFAULT,
    'revealjs_transitionSpeed': TransitionSpeed.DEFAULT,
    'source-highlighter': 'highlightjs'
};

// These are the same as for the ruby version
// http://asciidoctor.org/docs/user-manual/#ruby-api-options
const options = {
    safe: 'safe',
    backend: 'revealjs',
    base_dir: base_dir,
    to_dir: out_dir,
    mkdirs: 'true',
    attributes: Object.assign({}, own, asciidoctor_reveal_js)
};

fs.removeSync(out_dir);
asciidoctor.convertFile('slides/presentation.adoc', options);