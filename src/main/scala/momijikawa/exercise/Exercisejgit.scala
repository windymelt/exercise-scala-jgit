package momijikawa.exercise

import org.eclipse.jgit.lib._
import org.eclipse.jgit.api._
import org.eclipse.jgit.api.errors._
import scala.collection.JavaConverters._
import java.io.File

import org.eclipse.jgit.merge.MergeStrategy

object Exercisejgit extends App {
  /*
  まずCloneなりローカルなレポジトリを教えるなりして生成したgitのインスタンスを叩くことで各種レポジトリ操作を実行する。
  コマンドはメソッドチェインで構築し、call()で実行する。
  やらかすと例外を吐いて無慈悲に死ぬ仕様になっているっぽい。
   */
  val repoDir: String = "test-exercisejgit" // ~/$repoDir/をレポジトリとする
  val originUri: String = "https://github.com/windymelt/myrt.git"

  val repoExists = false // 説明のため。falseだとクローンする

  val workingTree: File = new File(System.getProperty("user.home") + s"/$repoDir/")
  // レポジトリ操作はGitインスタンスを通じて行う
  val git: Git = repoExists match {
    case false ⇒
      new CloneCommand()
        .setDirectory(workingTree)
        .setURI(originUri)
        .call()

    case true ⇒
      new Git(
        new RepositoryBuilder()
          .setWorkTree(workingTree)
          .build()
      )
  }

  var refList: Seq[Ref] = git.branchList().call().asScala.seq
  refList.foreach {
    ref ⇒
      println(ref.getName)
  }

  /* おおっと、ここでローカルレポジトリが変更された */

  git.commit()
    .setAuthor("nanashi-gonbei", "example@example.com")
    .setMessage("All your base are belong to us")
    .call()

  git.fetch()
    .call()

  git.merge()
    .include(git.getRepository.getRef("refs/remotes/origin/master"))
    .setFastForward(MergeCommand.FastForwardMode.FF)
    .setStrategy(MergeStrategy.RECURSIVE)
    .setSquash(false)
    .call()

  try {
    git.push()
      .call()
  } catch {
    case e: GitAPIException ⇒
      // Do something
  }
}
