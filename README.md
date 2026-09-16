# Bin Lu, this is for you

> Lu Bin, this is for you!  
> In this mod, you can experience the life of a farmer.  
> Besides, there are some tricks I have prepared for the player.  
> Just enjoy it!
>
> —— The author is very low key, you just need know Lu Bin is his defeated opponent!

**LuMod**（模组 ID：`lumod`）是一个面向 Minecraft 1.18.2 的 Forge 模组，主打"种田 + 整蛊"双修：一半是田园牧歌，一半是深渊凝视。

---

## ✨ 特性一览

### 🌾 农民之光（FarmerEvent）

| 事件 | 效果 |
|---|---|
| 🚜 踩踏耕地 | 系统连发三条道德谴责广播，随后立即送玩家上路 |
| ⛏️ 锄头开荒 | 对草方块/泥土使用锄头 → +1 经验点，外加彩虹屁两条 |
| 🌱 播种小麦 | 在耕地上种下小麦种子 → 获得一句由衷的夸奖 |
| 🌾 丰收预警 | 小麦长到接近成熟（7 级及以上）时，提醒 100 格内最近的玩家："附近的麦子快熟了！" |
| 🗑️ 乱扔垃圾检测 | 丢弃小麦种子 → 收到一句 "don't throw waste!" 的亲切问候 |

### 🎭 恶作剧彩蛋（TrickEvents）

- 🔊 **音效替换**：所有实体的音效统一替换为苦力怕（Creeper）的嘶嘶声——走哪响哪，听起来全场都在给你倒计时。
- 🐺 **狼生逆袭**：两只狼繁殖出的不再是狼崽，而是一只小鸡。达尔文看了都沉默。

### 🗡️ 主线玩法（Main）

- **进服问候**：玩家登录时收到一条热情洋溢的招呼语，血量被温柔地回满到 20。
- **开荒大礼包**：物品栏为空时自动初始化——钻石锄 ×1、小麦种子 ×64、骨粉 ×64、木剑 ×1。种田人，锄头魂。
- **剑气传送**：手持任意剑右键物品栏 → 朝视线方向传送最多 20 格。跑图神器，也是坑队友神器。
- **死亡豁免**：玩家的 `LivingDeathEvent` 被取消，配合"踩耕地处死"食用，风味更佳。

### 💎 专属物品（LubinItem）

- **Lubin**（`lumod:lubin`）：本模组的图腾级物品，收录在杂项创造标签页中，拥有专属贴图。它做什么？它什么都不做。它只是一种态度。

---

## 📦 版本信息

| 项目 | 内容 |
|---|---|
| Minecraft | **1.18.2** |
| Forge | **40.x**（`loaderVersion="[40,)"`） |
| Java | **17**（1.18.2 Forge 的要求） |
| 资源包格式 | pack_format 9 |
| 模组 ID | `lumod` |
| License | MIT |

> 版本判定依据：`mods.toml` 中声明 `versionRange="[1.18.2,1.19)"`，`pack.mcmeta` 中 `pack_format: 9`，代码使用 `Level#isClientSide()`、`player.sendMessage(Component, UUID)` 等 1.18.x API。

---

## 🚧 缺失与待完善内容

以下内容在当前仓库中**缺失或有占位**，请知悉：

- ❌ **没有构建系统**：仓库只有 `main`（即 `src/main` 的内容），缺少 `build.gradle`、`settings.gradle`、`gradle wrapper` 等 MDK 文件，无法直接编译。如需构建，请将本目录合并进 Forge 1.18.2 的 MDK。
- ❌ **`@Mod("lumod")` 重复标注**：`Main`、`FarmerEvent`、`TrickEvents` 三个类都标注了 `@Mod("lumod")`，同一 mod ID 出现多次会导致 FML 启动时报 Duplicate Mods 错误。正确做法是仅 `Main` 保留 `@Mod`，其余类使用 `@Mod.EventBusSubscriber(modid = "lumod")`。
- ⚠️ **LuBlock 是空壳**：只声明了 `DeferredRegister<Block>`，没有注册任何方块，也没有调用 `register(eventBus)`，属于预留坑位。
- ⚠️ **语言文件仅英文**：`en_us.json` 中只有 `item.lumod.lubin` 一条，暂无 `zh_cn.json`。
- ⚠️ **杂项小问题**：`TrickEvents#reproduce` 中 `EntityType.CHICKEN.create(...)` 可能返回 null 但未判空；部分代码存在 `info.toString()` 这类冗余调用。
- ⚠️ **无截图**：README 缺少运行截图，欢迎补充。

---

## 🔧 构建 / 安装

1. 下载 [Forge 1.18.2 MDK](https://files.minecraftforge.net/)（Gradle 7.x + JDK 17 环境）。
2. 将本仓库 `main/` 目录下的内容放入 MDK 的 `src/main/`。
3. 修正上文提到的 `@Mod` 重复标注问题（否则无法启动）。
4. `gradlew runClient` 启动游戏，或者 `gradlew build` 打出 jar 放进 `mods/` 文件夹。

---

## ⚠️ 免责声明

- 这是一个**整蛊向**模组，含有限制玩家行为（处死、清空物品栏）的机制，请勿在正经服务器上使用。
- 踩耕地即死不是 bug，是 feature。
- 因本模组导致友谊破裂的，作者概不负责。

---

## 🙏 致谢

- 感谢 **Lu Bin** 灵感来源般的存在。
- 感谢 Minecraft Forge 提供的框架。
- 最后，感谢每一位被种田玩法感化（物理）的玩家。

*Just enjoy it!* 🌾
