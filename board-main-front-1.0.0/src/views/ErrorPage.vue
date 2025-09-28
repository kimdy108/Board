<template>
  <div class="error-display"></div>
  <div class="terminal">
    <h1>Server Error</h1>
    <p class="output">죄송합니다. 요청하신 페이지를 찾을 수 없습니다.</p>
    <p class="output">서버 상태를 확인해주세요.</p>
  </div>
</template>

<script setup></script>

<style scoped>
.error-display {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: linear-gradient(
    -45deg,
    #7303c0,
    #00f260,
    #19547b,
    #fdbb2d,
    #b06ab3,
    #0575e6,
    #ec38bc,
    #8dc26f,
    #fdfc47
  );
  background-size: 400% 400%;
  animation: colorChange 20s ease infinite;
}

@keyframes colorChange {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

.terminal {
  box-sizing: inherit;
  position: absolute;
  height: 100%;
  width: 100%;
  font-size: 30px;
  padding-right: 20%;
  max-width: 100%;
  padding: 4rem;
  text-transform: uppercase;
  text-align: center;
  top: 0;
  left: 0;
  color: red;
  text-shadow:
    0 0 1px white,
    0 0 2px red;
}
.output {
  color: white;
}
</style>
