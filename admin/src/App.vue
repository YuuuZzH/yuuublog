<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted() {
    var OriginTitile = document.title,
      titleTime;

    document.addEventListener("visibilitychange", function () {
      if (document.hidden) {
        document.title = " 客官，别走吖 Σ(っ °Д °;) っ ";
        clearTimeout(titleTime);
      } else {
        document.title = "(/≧▽≦/) 又见到啦！";
        titleTime = setTimeout(function () {
          document.title = OriginTitile;
        }, 2000);
      }
    });
  },
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  }
}
</script>
